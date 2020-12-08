package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.codec.S_ResponseContext;
import com.dercg.netty.transport.codec.StatusCode;
import com.dercg.netty.transport.protocol.server_module_msg;
import com.dercg.netty.transport.util.CloseUtil;
import com.dercg.netty.transport.util.SystemTimeUtil;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class S_ServerSessionMgr extends SessionMgr {
    private Map<Channel, S_ServerSessionInfo> sessions = new ConcurrentHashMap<>();

    protected ChannelWriteMgr channelWriteMgr;

    public S_ServerSessionMgr(ChannelWriteMgr channelWriteMgr) {
        this.channelWriteMgr = channelWriteMgr;
    }

    public void addSession(S_ServerSessionInfo session) {
        sessions.put(session.getChannel(), session);
    }

    public void saveLastResult(long requestId, Channel channel, GeneratedMessageV3 result) {
        S_ServerSessionInfo session = sessions.get(channel);

        if (session != null) {
            session.setResult(result);
            session.setRequestId(requestId);
        }
    }

    public GeneratedMessageV3 getLastResult(long requestId, Channel channel) {
        S_ServerSessionInfo session = sessions.get(channel);
        if (session == null) {
            return null;
        }

        if (requestId != session.getRequestId()) {
            return null;
        }

        return session.getResult();
    }

    public S_ServerSessionInfo getSession(Channel channel) {
        return sessions.get(channel);
    }

    public long getRequestId(Channel channel) {
        S_ServerSessionInfo session = sessions.get(channel);
        if (session == null) {
            return 0;
        }

        return session.getRequestId();
    }

    public S_ServerSessionInfo removeSession(Channel channel) {
        return sessions.remove(channel);
    }

    public void closeTimeoutSession() {
        for (Map.Entry<Channel, S_ServerSessionInfo> entry : sessions.entrySet()) {
            S_ServerSessionInfo session = entry.getValue();
            int curTime = SystemTimeUtil.getTimestamp();
            int lastTime = session.getLastPingSec();
            if (curTime - lastTime > headSecond) {
                System.out.println("心跳超时，服务器端关闭会话");
                removeSession(entry.getKey());
                CloseUtil.closeQuietly(entry.getKey());
            }
        }
    }

    public boolean pingHandler(Channel channel, long requestId) {
        S_ServerSessionInfo sessionInfo = sessions.get(channel);
        if (sessionInfo == null) {
            removeSession(channel);
            CloseUtil.closeQuietly(channel);
            return true;
        }

        sessionInfo.setLastPingSec(SystemTimeUtil.getTimestamp());
        server_module_msg.server_module_ack.Builder builder = server_module_msg.server_module_ack.newBuilder();
        sendMsg(requestId, StatusCode.SUCCESS, channel, builder.build());
        return true;
    }

    public void sendMsg(long requestId, byte status, Channel channel, GeneratedMessageV3 proto) {
        S_ResponseContext context = new S_ResponseContext();
        context.setRequestId(requestId);
        context.setStatus(status);
        context.setResult(proto);
        channelWriteMgr.writeAndFlush(channel, context);
    }
}
