package com.dercg.netty.transport.queue;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

public class NonLockQueue {
    private static Disruptor<EventInfo> disruptor;
    private static RingBuffer<EventInfo> ringBuffer;
    private static final int INIT_LOGIC_EVENT_CAPACITY = 1024 * 128;

    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

    public static void start(QueueServerConsumer eventHandler, String ip, int port) throws Exception {
        DefaultEventFactory eventFactory = new DefaultEventFactory();
        eventHandler.init();
        ThreadFactory guavaThreadFactory = new ThreadFactoryBuilder().setNameFormat("S_NonLockQueue-pool-").build();

        disruptor = new Disruptor<EventInfo>(eventFactory, INIT_LOGIC_EVENT_CAPACITY, guavaThreadFactory, ProducerType.MULTI, YIELDING_WAIT);
        disruptor.handleEventsWith(eventHandler);

        ringBuffer = disruptor.getRingBuffer();
        disruptor.start();
        eventHandler.start(ip, port);
    }

    public static void publish(EventInfo eventInfo) {
        long sequence = ringBuffer.next();

        try {
            EventInfo newEventInfo = ringBuffer.get(sequence);

            newEventInfo.setRequestId(eventInfo.getRequestId());
            newEventInfo.setBody(eventInfo.getBody());
            newEventInfo.setEventType(eventInfo.getEventType());
            newEventInfo.setChannel(eventInfo.getChannel());
            newEventInfo.setProtoEnum(eventInfo.getProtoEnum());
        } finally {
            ringBuffer.publish(sequence);
        }
    }

    public static void stop() {
        disruptor.shutdown();
    }
}
