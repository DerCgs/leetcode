package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.C_ClientSessionMgr;
import com.dercg.netty.transport.mgr.C_ConnectMgr;
import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.util.CRCUtil;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.nio.ByteBuffer;

public class C_ClientEncoder extends EncoderBase {
    private final C_ClientSessionMgr sessionMgr;
    private final ProtoHandlerMgr protoHandlerMgr;

    public C_ClientEncoder(C_ConnectMgr connectMgr) {
        this.sessionMgr = connectMgr.getClientSessionMgr();
        this.protoHandlerMgr = connectMgr.getProtoHandlerMgr();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof GeneratedMessageV3) {
            GeneratedMessageV3 GeneratedMessageV3 = (GeneratedMessageV3) msg;
            writeImpl(ctx, promise, GeneratedMessageV3);
        } else {
            throw new Exception("not support parameter");
        }
    }

    private void writeImpl(ChannelHandlerContext ctx, ChannelPromise promise, GeneratedMessageV3 GeneratedMessageV3) throws Exception {
        int protoEnumInt = protoHandlerMgr.getProtoEnumInt(GeneratedMessageV3.getClass());
        int protoLength = GeneratedMessageV3.getSerializedSize();

        int finalProtoLen = 0;
        ByteBuf byteBuf = null;

        // requestId 8,protoint 4,sign 8 以及 finalProtoLen的长度 4
        finalProtoLen = protoLength + 20;
        byteBuf = ctx.alloc().directBuffer(finalProtoLen + 4, finalProtoLen + 4);
        byteBuf.writeInt(finalProtoLen);
        long requestId = sessionMgr.getRequestId(ctx.channel());
        byteBuf.writeLong(requestId);
        byteBuf.writeInt(protoEnumInt);

        // 计算sign的值
        ByteBuffer byteBuffer = ByteBuffer.allocate(finalProtoLen - 8);
        byteBuffer.putLong(requestId);
        byteBuffer.putLong(protoEnumInt);
        byteBuffer.put(GeneratedMessageV3.toByteArray());
        long sign = CRCUtil.Generic(byteBuffer.array());
        byteBuf.writeLong(sign);

        try (ByteBufOutputStream out = new ByteBufOutputStream(byteBuf)) {
            GeneratedMessageV3.writeTo(out);

            super.write(ctx, out.buffer(), promise);
        }
    }
}
