package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.ClientSessionMgr;
import com.dercg.netty.transport.mgr.ConnectMgr;
import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.util.CRCUtil;
import com.google.protobuf.GeneratedMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.nio.ByteBuffer;

public class ClientEncoder extends EncoderBase {
    private final ClientSessionMgr sessionMgr;
    private final ProtoHandlerMgr protoHandlerMgr;

    public ClientEncoder(ConnectMgr connectMgr) {
        this.sessionMgr = connectMgr.getClientSessionMgr();
        this.protoHandlerMgr = connectMgr.getProtoHandlerMgr();
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof GeneratedMessage) {
            GeneratedMessage generatedMessage = (GeneratedMessage) msg;
            writeImpl(ctx, promise, generatedMessage);
        } else {
            throw new Exception("not support parameter");
        }
    }

    private void writeImpl(ChannelHandlerContext ctx, ChannelPromise promise, GeneratedMessage generatedMessage) throws Exception {
        int protoEnumInt = protoHandlerMgr.getProtoEnumInt(generatedMessage.getClass());
        int protoLength = generatedMessage.getSerializedSize();

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
        byteBuffer.put(generatedMessage.toByteArray());
        long sign = CRCUtil.Generic(byteBuffer.array());
        byteBuf.writeLong(sign);

        try (ByteBufOutputStream out = new ByteBufOutputStream(byteBuf)) {
            generatedMessage.writeTo(out);

            super.write(ctx, out.buffer(), promise);
        }
    }
}
