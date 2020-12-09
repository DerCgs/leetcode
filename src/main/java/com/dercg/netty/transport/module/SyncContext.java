package com.dercg.netty.transport.module;

import com.dercg.netty.transport.codec.ResultInfo;

import java.util.concurrent.CountDownLatch;

public class SyncContext {
    private long syncId;
    // 都在java.util.concurrent下，用来表示代码运行到某个点上，CountDownLatch则不是，某线程运行到某个点上之后，只是给某个数值-1而已，该线程继续运行
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
