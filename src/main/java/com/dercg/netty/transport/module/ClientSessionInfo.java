package com.dercg.netty.transport.module;

import com.dercg.netty.transport.module.SyncContext;
import io.netty.channel.Channel;

public class ClientSessionInfo {
    private Channel channel;
    private SyncContext syncContext;
    private String serverName;
    private String remoteIp;
    private int remotePort;
    private int lastPingSec;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public SyncContext getSyncContext() {
        return syncContext;
    }

    public void setSyncContext(SyncContext syncContext) {
        this.syncContext = syncContext;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public int getLastPingSec() {
        return lastPingSec;
    }

    public void setLastPingSec(int lastPingSec) {
        this.lastPingSec = lastPingSec;
    }

    public String getAddress() {
        return this.remoteIp + this.remotePort;
    }
}
