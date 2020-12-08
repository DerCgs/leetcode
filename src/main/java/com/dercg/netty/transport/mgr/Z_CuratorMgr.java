package com.dercg.netty.transport.mgr;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Z_CuratorMgr {

    private CuratorFramework client;

    private static final int sleepMs = 1000;

    private static final int retryCount = 3;

    private static final String DefaultConnectionString = "";

    public Z_CuratorMgr() {
        this(DefaultConnectionString);
    }

    public Z_CuratorMgr(String connectString) {
        client = CuratorFrameworkFactory.newClient(connectString, new ExponentialBackoffRetry(sleepMs, retryCount));
        client.start();
    }

    public CuratorFramework getCuratorClient() {
        return client;
    }

    public void close() {
        client.close();
    }
}