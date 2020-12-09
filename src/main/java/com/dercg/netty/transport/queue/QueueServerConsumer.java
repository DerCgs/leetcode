package com.dercg.netty.transport.queue;

import com.dercg.netty.transport.module.ModuleServerService;
import com.lmax.disruptor.EventHandler;

import java.util.EnumMap;
import java.util.Map;

public class QueueServerConsumer implements EventHandler<EventInfo> {

    protected ModuleServerService moduleServerService;
    protected final Map<EventType, AsynchronousEventHandler> eventHandlers = new EnumMap<>(EventType.class);

    public QueueServerConsumer(ModuleServerService moduleServerService) {
        this.moduleServerService = moduleServerService;
    }

    protected void init() throws Exception {
        eventHandlers.put(EventType.CLIENT_REGISTER, moduleServerService::onClientRegister);
        eventHandlers.put(EventType.CLIENT_PROTO_COMING, moduleServerService::onClientProtoCome);
        eventHandlers.put(EventType.CLIENT_DISCONNECT, moduleServerService::onClientDisconnect);

        moduleServerService.init();
    }

    @Override
    public void onEvent(EventInfo event, long sequence, boolean endOfBatch) throws Exception {
        EventType eventType = event.getEventType();
        AsynchronousEventHandler handler = eventHandlers.get(eventType);
        handler.onEvent(event);
    }

    public void start(String ip, int port) throws Exception {
        moduleServerService.startServer(ip, port);
    }
}
