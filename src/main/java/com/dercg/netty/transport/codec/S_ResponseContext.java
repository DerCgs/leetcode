package com.dercg.netty.transport.codec;

import com.google.protobuf.GeneratedMessageV3;

public class S_ResponseContext {
    private long requestId;
    private byte status;

    private GeneratedMessageV3 result;

    public S_ResponseContext() {
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public GeneratedMessageV3 getResult() {
        return result;
    }

    public void setResult(GeneratedMessageV3 result) {
        this.result = result;
    }
}
