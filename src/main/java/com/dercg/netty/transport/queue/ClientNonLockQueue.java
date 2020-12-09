package com.dercg.netty.transport.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

public class ClientNonLockQueue {
    private static Disruptor<ClientEventInfo> disruptor;
    private static RingBuffer<ClientEventInfo> ringBuffer;

    private static final int INIT_LOCAL_EVENT_CAPACITY = 1024 * 128;
    private static final WaitStrategy YIELDING_WAIT = new SleepingWaitStrategy();

    public static void start(QueueClientConsumer eventHandler) throws Exception {
        ThreadFactory guavaThreadFactory = new ThreadFactoryBuilder().setNameFormat("C_NonLockQueue-pool-").build();
        ClientEventFactory eventFactory = new ClientEventFactory();

        disruptor = new Disruptor<ClientEventInfo>(eventFactory, INIT_LOCAL_EVENT_CAPACITY, guavaThreadFactory, ProducerType.MULTI, YIELDING_WAIT);
        disruptor.handleEventsWith(eventHandler);

        ringBuffer = disruptor.getRingBuffer();

        eventHandler.init();
        disruptor.start();
    }

    public static void publish(ClientEventInfo eventInfo) {
        long sequence = ringBuffer.next();
        try {
            ClientEventInfo newEventInfo = ringBuffer.get(sequence);
            newEventInfo.setId(eventInfo.getId());
            newEventInfo.setServiceName(eventInfo.getServiceName());
            newEventInfo.setBody(eventInfo.getBody());
            newEventInfo.setStrategyType(eventInfo.getStrategyType());
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public static void stop() {
        disruptor.shutdown();
    }
}