package com.dercg.netty.transport.module;

import com.dercg.netty.transport.codec.StatusCode;
import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.mgr.S_ServerSessionInfo;
import com.dercg.netty.transport.mgr.S_ServerSessionMgr;
import com.dercg.netty.transport.mgr.S_TcpServerMgr;
import com.dercg.netty.transport.protocol.ProtoType;
import com.dercg.netty.transport.protocol.server_module_msg;
import com.dercg.netty.transport.queue.EventInfo;
import com.dercg.netty.transport.service.EndPoint;
import com.dercg.netty.transport.service.ServiceEntry;
import com.dercg.netty.transport.service.ServiceRegistry;
import com.dercg.netty.transport.util.CloseUtil;
import com.dercg.netty.transport.util.SystemTimeUtil;
import com.google.protobuf.GeneratedMessageV3;


public abstract class ModuleServerService {
    protected ProtoHandlerMgr protoHandlerMgr;
    protected S_TcpServerMgr tcpServerMgr;
    protected ServiceRegistry serviceRegistry;
    protected S_ServerSessionMgr serverSessionMgr;

    public ModuleServerService(ProtoHandlerMgr protoHandlerMgr, S_TcpServerMgr tcpServerMgr, ServiceRegistry serviceRegistry) {
        this.protoHandlerMgr = protoHandlerMgr;
        this.tcpServerMgr = tcpServerMgr;
        this.serviceRegistry = serviceRegistry;
        this.serverSessionMgr = tcpServerMgr.getServerSessionMgr();
    }

    public void init() {
        registerProtoHandler();
    }

    protected void registerProtoHandler() {
        protoHandlerMgr.registerProto(ProtoType.SERVER_MODULE_PING, server_module_msg.server_module_ping.class);
        protoHandlerMgr.registerProto(ProtoType.SERVER_MODULE_ACK, server_module_msg.server_module_ack.class);
        registerProtoHandlerImpl();
    }

    protected abstract void registerProtoHandlerImpl();

    public S_ServerSessionMgr getServerSessionMgr() {
        return serverSessionMgr;
    }

    public void startServer(String ip, int port) throws Exception {
        EndPoint endPoint = tcpServerMgr.acceptService(ip, port);
        ServiceEntry entry = new ServiceEntry();
        entry.setServiceName(getServiceName());
        entry.setIp(endPoint.getIp());
        entry.setPort(endPoint.getPort());
// TODO
//        serviceRegistry.registerService(entry);
    }

    public void onClientProtoCome(EventInfo data) {
        GeneratedMessageV3 result = serverSessionMgr.getLastResult(data.getRequestId(), data.getChannel());
        if (result != null) {
            serverSessionMgr.sendMsg(data.getRequestId(), StatusCode.SUCCESS, data.getChannel(), result);
            return;
        }
        try {
            result = protoHandlerMgr.handleClientProto(data.getRequestId(), data.getProtoEnum(), data.getChannel(), data.getBody());
            serverSessionMgr.saveLastResult(data.getRequestId(), data.getChannel(), result);
            serverSessionMgr.sendMsg(data.getRequestId(), StatusCode.SUCCESS, data.getChannel(), result);
        } catch (Throwable ex) {
            ex.printStackTrace();
            serverSessionMgr.sendMsg(data.getRequestId(), StatusCode.EXCEPTION, data.getChannel(), result);
        }
    }

    public void onClientRegister(EventInfo data) {
        S_ServerSessionInfo session = new S_ServerSessionInfo();
        session.setChannel(data.getChannel());
        session.setLastPingSec(SystemTimeUtil.getTimestamp());

        serverSessionMgr.addSession(session);
    }

    public void onClientDisconnect(EventInfo data) {
        serverSessionMgr.removeSession(data.getChannel());
        CloseUtil.closeQuietly(data.getChannel());
    }

    protected abstract String getServiceName();
}
