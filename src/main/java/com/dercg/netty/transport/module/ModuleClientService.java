package com.dercg.netty.transport.module;

import com.dercg.netty.transport.Server;
import com.dercg.netty.transport.codec.ResultInfo;
import com.dercg.netty.transport.codec.StatusCode;
import com.dercg.netty.transport.mgr.C_ClientSessionMgr;
import com.dercg.netty.transport.mgr.C_ConnectMgr;
import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.protocol.ProtoType;
import com.dercg.netty.transport.protocol.server_module_msg;
import com.dercg.netty.transport.queue.ClientEventInfo;
import com.dercg.netty.transport.queue.ClientNonLockQueue;
import com.dercg.netty.transport.service.ProviderStrategyType;
import com.dercg.netty.transport.service.ServiceDiscover;
import com.dercg.netty.transport.service.ServiceEntry;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ModuleClientService {
    protected ProtoHandlerMgr protoHandlerMgr;
    protected C_ConnectMgr connectMgr;
    private C_ClientSessionMgr clientSessionMgr;
    private ServiceDiscover discover;

    private static final int DEFAULT_TIMEOUT = 30;

    private Map<Long, SyncContext> syncContexts = new ConcurrentHashMap<>();

    private AtomicLong syncGuid = new AtomicLong(0);

    public ModuleClientService(ProtoHandlerMgr protoHandlerMgr, C_ConnectMgr connectMgr, ServiceDiscover discover) {
        this.protoHandlerMgr = protoHandlerMgr;
        this.connectMgr = connectMgr;
        this.discover = discover;
        this.clientSessionMgr = connectMgr.getClientSessionMgr();
    }

    public SyncContext getSyncContexts(long syncId) {
        return this.syncContexts.get(syncId);
    }

    public ResultInfo sendSyncMsg(String serviceName, GeneratedMessageV3 proto, ProviderStrategyType strategyType) throws InterruptedException {
        long id = syncGuid.incrementAndGet();
        SyncContext context = new SyncContext();

        CountDownLatch latch = new CountDownLatch(1);

        context.setLatch(latch);
        context.setSyncId(id);

        ClientEventInfo eventInfo = new ClientEventInfo();

        eventInfo.setId(id);
        eventInfo.setServiceName(serviceName);
        eventInfo.setBody(proto);
        eventInfo.setStrategyType(strategyType);

        syncContexts.put(id, context);

        ClientNonLockQueue.publish(eventInfo);

        try {
            latch.await();
            return context.getResult();
        } finally {
            syncContexts.remove(id);
        }
    }

    public ResultInfo onClientSend(ClientEventInfo data, ServiceEntry entry) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        SyncContext context = new SyncContext();
        context.setSyncId(data.getId());
        context.setLatch(latch);

        Channel channel = connectMgr.connect(data.getServiceName(), entry.getIp(), entry.getPort());

        if (channel == null) {
            ResultInfo result = new ResultInfo();
            result.setErrorCode(StatusCode.CONNECTEXCEPTION);
            return result;
        }

        clientSessionMgr.updateSyncContext(context, channel);
        clientSessionMgr.sendMsg(channel, data.getBody());

        if (!context.getLatch().await(DEFAULT_TIMEOUT, TimeUnit.SECONDS)) {
            ResultInfo result = new ResultInfo();
            result.setErrorCode(StatusCode.TIMEOUT);
            return result;
        }

        return context.getResult();
    }

    public ServiceEntry getServiceAddress(String serviceName, ProviderStrategyType strategyType) {
        return Server.getServerEntry();
        // TODO
//        try {
//            ServiceInstance<ServiceEntry> instance = discover.getService(serviceName, strategyType);
//            if (instance == null) {
//                return null;
//            }
//            return instance.getPayload();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public void init() {
        registerProto();
    }

    protected void registerProto() {
        protoHandlerMgr.registerProto(ProtoType.SERVER_MODULE_PING, server_module_msg.server_module_ping.class);
        protoHandlerMgr.registerProto(ProtoType.SERVER_MODULE_ACK, server_module_msg.server_module_ack.class);
        registerProtoImpl();
    }

    protected abstract void registerProtoImpl();
}
