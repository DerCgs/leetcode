package com.dercg.netty.transport.codec;

import com.google.protobuf.GeneratedMessageV3;

public class ResultInfo {
    private byte errorCode;
    private String message;
    private GeneratedMessageV3 data;

    public byte getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(byte errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GeneratedMessageV3 getData() {
        return data;
    }

    public void setData(GeneratedMessageV3 data) {
        this.data = data;
    }
}
