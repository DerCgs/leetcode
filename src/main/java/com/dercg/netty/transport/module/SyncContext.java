package com.dercg.netty.transport.module;

import com.dercg.netty.transport.codec.ResultInfo;

import java.util.concurrent.CountDownLatch;

public class SyncContext {
    private long syncId;
    private CountDownLatch latch;
    private ResultInfo result;

    public SyncContext() {
    }

    public long getSyncId() {
        return syncId;
    }

    public void setSyncId(long syncId) {
        this.syncId = syncId;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {

    }

    public ResultInfo getResult() {
        return result;
    }

    public void setResult(ResultInfo result) {
        this.result = result;
        if (latch != null) {
            latch.countDown();
        }
    }
}
