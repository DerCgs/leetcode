package com.dercg.netty.transport.mgr;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

public class S_ServerSessionInfo {
    private long requestId;
    private Channel channel;
    private int lastPingSec;
    private GeneratedMessageV3 result;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getLastPingSec() {
        return lastPingSec;
    }

    public void setLastPingSec(int lastPingSec) {
        this.lastPingSec = lastPingSec;
    }

    public GeneratedMessageV3 getResult() {
        return result;
    }

    public void setResult(GeneratedMessageV3 result) {
        this.result = result;
    }
}
