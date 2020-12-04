package com.dercg.netty.transport.mgr;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSessionMgr {
    private final Map<String, Map<String, Channel>> serviceChannels = new ConcurrentHashMap<>();

    private final ChannelWriteMgr channelWriteMgr;

    private final Map<Channel, ClientSessionInfo> sessions = new ConcurrentHashMap<>();

    public ClientSessionMgr(ChannelWriteMgr channelWriteMgr) {
        this.channelWriteMgr = channelWriteMgr;
    }

    public void addSession(String serviceName, String address, ClientSessionInfo sessionInfo) {
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

    public ClientSessionInfo getSession(Channel channel) {
        return sessions.get(channel);
    }

    public long getRequestId(Channel channel) {
        ClientSessionInfo session = sessions.get(channel);
        return session.getSyncContext().getSyncId();
    }

}
