package com.dercg.netty.transport.module.bus;

import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.mgr.S_TcpServerMgr;
import com.dercg.netty.transport.module.ModuleServerService;
import com.dercg.netty.transport.protocol.user_login;
import com.dercg.netty.transport.service.ServiceRegistry;
import io.netty.channel.Channel;

public class UserService extends ModuleServerService {

    public static final String serviceName = "UserService";

    public UserService(ProtoHandlerMgr protohandlerMgr, S_TcpServerMgr tcpServerMgr, ServiceRegistry serviceRegister) throws Exception {

        super(protohandlerMgr, tcpServerMgr, serviceRegister);
    }

    @Override
    protected void registerProtoHandlerImpl() {
        protoHandlerMgr.registerHandler(user_login.user_login_request.class, this::accountLoginHandler);
        protoHandlerMgr.registerProto(user_login.user_login_result.class);
    }

    @Override
    protected String getServiceName() {
        return serviceName;
    }

    public user_login.user_login_result accountLoginHandler(long requestId, Channel channel, Object proto) {
        user_login.user_login_request p = (user_login.user_login_request) proto;

        user_login.user_login_result.Builder builder = user_login.user_login_result.newBuilder();
        // TODO
        String lotteryResult = "这里调用具体的业务实现";
        if (lotteryResult.contains("error")) {
            builder.setIsSuccess(false);
            builder.setMessage("失败");
        } else {
            builder.setIsSuccess(true);
            builder.setMessage("成功");
        }

        return builder.build();
    }

}
