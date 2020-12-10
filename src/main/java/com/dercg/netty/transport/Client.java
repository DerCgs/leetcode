package com.dercg.netty.transport;

import com.dercg.netty.transport.mgr.*;
import com.dercg.netty.transport.module.bus.UserClientConsumer;
import com.dercg.netty.transport.module.bus.UserClientService;
import com.dercg.netty.transport.protocol.user_login;
import com.dercg.netty.transport.queue.ClientNonLockQueue;
import com.dercg.netty.transport.service.ServiceDiscover;
import com.dercg.netty.transport.service.ServiceRoute;
import com.google.protobuf.GeneratedMessageV3;

public class Client {
    private volatile static boolean isStart = false;
    private static Object lockObj = new Object();
    private static UserClientService clientService;

    public static void init() throws Exception {
        if (isStart) return;
        synchronized (lockObj) {
            if (isStart) return;
            ProtoHandlerMgr protoHandlerMgr = new ProtoHandlerMgr();

            EventGroupMgr eventGroupMgr = new EventGroupMgr();
            ChannelWriteMgr channelWriteMgr = new ChannelWriteMgr();

            TimerMgr timerMgr = new TimerMgr();
//            Z_CuratorMgr curatorMgr = new Z_CuratorMgr();
//            Z_ServiceDiscoverMgr discoverMgr = new Z_ServiceDiscoverMgr(curatorMgr);

//            ServiceRoute serviceRote = new ServiceRoute();
//            ServiceDiscover discover = new ServiceDiscover(discoverMgr, serviceRote);
            C_ClientSessionMgr clientSessionMgr = new C_ClientSessionMgr(channelWriteMgr);
            C_ConnectMgr connectmgr = new C_ConnectMgr(eventGroupMgr, clientSessionMgr, timerMgr, protoHandlerMgr);

            clientService = new UserClientService(protoHandlerMgr, connectmgr, null);

            UserClientConsumer consumer = new UserClientConsumer(clientService);
            ClientNonLockQueue.start(consumer);
            isStart = true;

        }
    }

    public static user_login.user_login_result send() throws Exception {

        init();
        return clientService.accountLogin("pipeline", "1236978");
    }

    public static void main(String[] args) throws Exception {
        GeneratedMessageV3 message = Client.send();

        System.out.println(message.toString());
        long startTime = System.currentTimeMillis();
        for(int i=0; i<1000; i++) {
            message = Client.send();

            System.out.println(message.toString());
        }

        long endTime = System.currentTimeMillis();

        System.out.println("当前程序耗时："+ (endTime-startTime) + "ms");
    }
}
