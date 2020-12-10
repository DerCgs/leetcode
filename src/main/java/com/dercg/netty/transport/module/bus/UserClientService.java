package com.dercg.netty.transport.module.bus;

import com.dercg.netty.transport.codec.ResultInfo;
import com.dercg.netty.transport.codec.StatusCode;
import com.dercg.netty.transport.mgr.C_ConnectMgr;
import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.module.ModuleClientService;
import com.dercg.netty.transport.protocol.user_login;
import com.dercg.netty.transport.service.ProviderStrategyType;
import com.dercg.netty.transport.service.ServiceDiscover;

public class UserClientService extends ModuleClientService {
    public UserClientService(ProtoHandlerMgr protoHandlerMgr, C_ConnectMgr connectMgr, ServiceDiscover discover) {
        super(protoHandlerMgr, connectMgr, discover);
    }

    public user_login.user_login_result accountLogin(String account, String password) throws InterruptedException {
        user_login.user_login_request.Builder builder = user_login.user_login_request.newBuilder();

        builder.setAccount(account);
        builder.setPassword(password);

        ResultInfo resultInfo = this.sendSyncMsg(UserService.serviceName, builder.build(), ProviderStrategyType.ROUNDROBIN);

        if (resultInfo == null || resultInfo.getErrorCode() == StatusCode.CONNECTIONCLOSED) {
            return null;
        }

        return (user_login.user_login_result) resultInfo.getData();
    }

    @Override
    protected void registerProtoImpl() {
        protoHandlerMgr.registerProto(user_login.user_login_request.class);
        protoHandlerMgr.registerProto(user_login.user_login_result.class);
    }
}
