package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.ClientSessionMgr;
import com.dercg.netty.transport.mgr.ConnectMgr;

public class ServerDecoder extends EncoderBase {
    private static final int MAX_CLIENT_PACKAGE_LENGTH = 1024;

    private ClientSessionMgr clientSeesionMgr;
    private ConnectMgr connectMgr;
}
