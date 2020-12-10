package com.dercg.netty.transport.module.bus;

import com.dercg.netty.transport.module.ModuleClientService;
import com.dercg.netty.transport.queue.QueueClientConsumer;

public class UserClientConsumer extends QueueClientConsumer {
    public UserClientConsumer(ModuleClientService moduleClientService) {
        super(moduleClientService);
    }
}
