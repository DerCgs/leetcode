package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.codec.ResultInfo;
import com.dercg.netty.transport.module.SyncContext;
import com.dercg.netty.transport.protocol.server_module_msg;
import com.dercg.netty.transport.util.CloseUtil;
import com.dercg.netty.transport.util.SystemTimeUtil;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class C_ClientSessionMgr extends SessionMgr{
    private final Map<String, Map<String, Channel>> serviceChannels = new ConcurrentHashMap<>();

    private final ChannelWriteMgr channelWriteMgr;

    private final Map<Channel, C_ClientSessionInfo> sessions = new ConcurrentHashMap<>();

    public C_ClientSessionMgr(ChannelWriteMgr channelWriteMgr) {
        this.channelWriteMgr = channelWriteMgr;
    }

    public void addSession(String serviceName, String address, C_ClientSessionInfo sessionInfo) {
        sessions.put(sessionInfo.getChannel(), sessionInfo);

        Map<String, Channel> channels = serviceChannels.get(serviceName);

        if (channels != null) {
            channels.putIfAbsent(address, sessionInfo.getChannel());
        } else {
            channels = new ConcurrentHashMap<>();
            channels.put(address, sessionInfo.getChannel());
            serviceChannels.putIfAbsent(serviceName, channels);
        }
    }

    public C_ClientSessionInfo getSession(Channel channel) {
        return sessions.get(channel);
    }

    public long getRequestId(Channel channel) {
        C_ClientSessionInfo session = sessions.get(channel);
        return session.getSyncContext().getSyncId();
    }

    public Channel getChannel(String serverName, String address) {
        Map<String, Channel> channels = serviceChannels.get(serverName);

        if (channels == null) {
            return null;
        }

        return channels.get(address);
    }

    public void removeSession(Channel channel) {
        try {
            C_ClientSessionInfo sessionInfo = getSession(channel);

            if (sessionInfo == null) {
                return;
            }

            sessions.remove(channel);

            Map<String, Channel> channels = serviceChannels.get(sessionInfo.getServerName());

            if (channels == null) {
                return;
            }
            if (channel.equals(channels.get(sessionInfo.getAddress()))) {
                channels.remove(sessionInfo.getAddress());
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void updateSyncContext(SyncContext syncContext, Channel channel) {
        C_ClientSessionInfo session = sessions.get(channel);
        if (session != null) {
            session.setSyncContext(syncContext);
        }
    }

    // 发送心跳包
    public void sendPing() {
        for (Map.Entry<Channel, C_ClientSessionInfo> entry : sessions.entrySet()) {
            server_module_msg.server_module_ping.Builder builder = server_module_msg.server_module_ping.newBuilder();
            sendMsg(entry.getKey(), builder.build());
        }
    }

    // 检测超时的session
    public void checkTimeoutSession() {
        for (Map.Entry<Channel, C_ClientSessionInfo> entry : sessions.entrySet()) {
            C_ClientSessionInfo sessionInfo = entry.getValue();

            int curTime = SystemTimeUtil.getTimestamp();

            if (curTime - sessionInfo.getLastPingSec() > headSecond) {
                System.out.println(sessionInfo.getChannel().id() + "会话超时，客户端关闭会话");
                removeSession(sessionInfo.getChannel());
                CloseUtil.closeQuietly(sessionInfo.getChannel());
            }
        }
    }

    public void onServerProtoCome(long requestId, Channel channel, ResultInfo result) {
        C_ClientSessionInfo sessionInfo = getSession(channel);
        if (sessionInfo == null) {
            return;
        }
        SyncContext context = sessionInfo.getSyncContext();
        if (context == null) {
            return;
        }
        if (context.getSyncId() != requestId) {
            return;
        }
        context.setResult(result);
    }

    public void sendMsg(Channel channel, Object proto) {
        channelWriteMgr.writeAndFlush(channel, proto);
    }

    public void pingHandler(Channel channel) {
        C_ClientSessionInfo sessionInfo = getSession(channel);

        if (sessionInfo == null) {
            return;
        }

        sessionInfo.setLastPingSec(SystemTimeUtil.getTimestamp());
    }

}
