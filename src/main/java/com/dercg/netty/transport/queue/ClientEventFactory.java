package com.dercg.netty.transport.queue;

import com.lmax.disruptor.EventFactory;

public class ClientEventFactory implements EventFactory<ClientEventInfo> {

    @Override
    public ClientEventInfo newInstance() {
        return new ClientEventInfo();
    }
}
