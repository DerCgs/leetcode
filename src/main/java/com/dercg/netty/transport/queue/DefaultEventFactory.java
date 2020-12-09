package com.dercg.netty.transport.queue;

import com.lmax.disruptor.EventFactory;

public class DefaultEventFactory implements EventFactory<EventInfo> {
    @Override
    public EventInfo newInstance() {
        return new EventInfo();
    }
}
