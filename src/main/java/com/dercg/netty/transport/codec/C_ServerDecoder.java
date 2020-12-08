package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.C_ClientSessionMgr;
import com.dercg.netty.transport.mgr.C_ConnectMgr;
import com.dercg.netty.transport.protocol.ProtoType;
import com.dercg.netty.transport.util.CRCUtil;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;

public class C_ServerDecoder extends DecoderBase {
    private static final int MAX_CLIENT_PACKAGE_LENGTH = 1024;

    private C_ClientSessionMgr clientSessionMgr;
    private C_ConnectMgr connectMgr;

    public C_ServerDecoder(C_ConnectMgr connectMgr) {
        super(MAX_CLIENT_PACKAGE_LENGTH, connectMgr.getProtoHandlerMgr());
        this.connectMgr = connectMgr;
        this.clientSessionMgr = connectMgr.getClientSessionMgr();
    }

    @Override
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        ByteBuf byteBuf = buffer.slice(index, length);

        long requestId = byteBuf.readLong();
        int protoEnumInt = byteBuf.readInt();
        long sign = byteBuf.readLong();

        ByteBuffer byteBuffer = ByteBuffer.allocate(length - 8);

        GeneratedMessageV3 result = null;
        ResultInfo resultInfo = new ResultInfo();

        if (protoEnumInt == ProtoType.SERVER_MODULE_ACK) {
            clientSessionMgr.pingHandler(ctx.channel());
            return Unpooled.EMPTY_BUFFER;
        }

        if (length > 20) {
            try {
                byteBuffer.putLong(requestId);
                byteBuffer.putInt(protoEnumInt);
                result = this.readFrame(byteBuf, protoEnumInt);
                byteBuffer.put(result.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
                resultInfo.setErrorCode(StatusCode.EXCEPTION);
                clientSessionMgr.onServerProtoCome(requestId, ctx.channel(), resultInfo);
                return Unpooled.EMPTY_BUFFER;
            }
        } else {
            byteBuffer.putLong(requestId);
            byteBuffer.putInt(protoEnumInt);
        }

        long tmpSign = CRCUtil.Generic(byteBuf.array());

        if (sign != tmpSign) {
            resultInfo.setErrorCode(StatusCode.SIGNERROR);
            clientSessionMgr.onServerProtoCome(requestId, ctx.channel(), resultInfo);
            return Unpooled.EMPTY_BUFFER;
        }

        resultInfo.setErrorCode(StatusCode.SUCCESS);
        resultInfo.setData(result);
        clientSessionMgr.onServerProtoCome(requestId, ctx.channel(), resultInfo);
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        connectMgr.onDisconnect(ctx.channel());
    }
}
