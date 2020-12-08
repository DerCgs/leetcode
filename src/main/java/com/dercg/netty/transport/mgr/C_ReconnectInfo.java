package com.dercg.netty.transport.mgr;

import io.netty.channel.Channel;

public class C_ReconnectInfo {
    private Channel channel;

    private long startTime;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
