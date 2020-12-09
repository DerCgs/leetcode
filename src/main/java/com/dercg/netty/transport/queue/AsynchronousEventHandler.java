package com.dercg.netty.transport.queue;

public interface AsynchronousEventHandler {
    void onEvent(EventInfo logicEvent);
}
