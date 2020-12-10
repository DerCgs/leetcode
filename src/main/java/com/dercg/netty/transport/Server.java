package com.dercg.netty.transport;

import com.dercg.netty.transport.mgr.*;
import com.dercg.netty.transport.module.bus.UserConsumer;
import com.dercg.netty.transport.module.bus.UserService;
import com.dercg.netty.transport.queue.NonLockQueue;
import com.dercg.netty.transport.service.ServiceEntry;
import com.dercg.netty.transport.service.ServiceRegistry;

public class Server {
    public static void main(String[] args) throws Exception {

        System.out.println("netty服务端开始启动");

        ProtoHandlerMgr protoHandlerMgr = new ProtoHandlerMgr();

        EventGroupMgr eventGroupMgr = new EventGroupMgr();
        ChannelWriteMgr channelWriteMgr = new ChannelWriteMgr();
        S_ServerSessionMgr serverSessionMgr = new S_ServerSessionMgr(channelWriteMgr);
        TimerMgr timerMgr = new TimerMgr();
        S_TcpServerMgr tcpServerMgr = new S_TcpServerMgr(eventGroupMgr, serverSessionMgr, timerMgr, protoHandlerMgr);

//        Z_CuratorMgr curatorMgr = new Z_CuratorMgr();
//        Z_ServiceDiscoverMgr discoverMgr = new Z_ServiceDiscoverMgr(curatorMgr);
//        ServiceRegistry serverRegister = new ServiceRegistry(discoverMgr);
        ServiceRegistry serverRegister = null;
        UserService userService = new UserService(protoHandlerMgr, tcpServerMgr, serverRegister);

        NonLockQueue.start(new UserConsumer(userService), "127.0.0.1", 8001);
    }

    public static ServiceEntry getServerEntry() {
        ServiceEntry entry = new ServiceEntry();
        entry.setPort(8001);
        entry.setIp("127.0.0.1");
        entry.setServiceName("UserService");
        return entry;
    }
}
