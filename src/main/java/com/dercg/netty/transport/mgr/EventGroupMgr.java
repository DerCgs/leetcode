package com.dercg.netty.transport.mgr;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class EventGroupMgr {
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1, (Runnable r) -> new Thread(r, "ACCEPT_THREAD"));

    private final EventLoopGroup workGroup = new NioEventLoopGroup(1, new ThreadFactory() {
        private final AtomicInteger threadIndex = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            int tempIndex = threadIndex.incrementAndGet();
            return new Thread(r, String.format("IO_THREAD_%d", tempIndex));
        }
    });

    public EventGroupMgr() {
    }

    public EventLoopGroup getBossGroup() {
        return this.bossGroup;
    }

    public EventLoopGroup getWorkGroup() {
        return this.workGroup;
    }
}
