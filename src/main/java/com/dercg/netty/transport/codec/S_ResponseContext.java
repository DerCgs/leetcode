package com.dercg.netty.transport.codec;

import com.google.protobuf.GeneratedMessage;

public class S_ResponseContext {
    private long requestId;
    private byte status;

    private GeneratedMessage result;

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

    public GeneratedMessage getResult() {
        return result;
    }

    public void setResult(GeneratedMessage result) {
        this.result = result;
    }
}
