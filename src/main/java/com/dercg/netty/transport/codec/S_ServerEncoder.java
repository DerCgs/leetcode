package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.util.CRCUtil;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.nio.ByteBuffer;

public class S_ServerEncoder extends EncoderBase {
    private ProtoHandlerMgr protoHandlerMgr;

    public S_ServerEncoder(ProtoHandlerMgr protoHandlerMgr) {
        this.protoHandlerMgr = protoHandlerMgr;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof S_ResponseContext) {
            S_ResponseContext context = (S_ResponseContext) msg;

            writeImpl(ctx, promise, context.getRequestId(), context.getStatus(), context.getResult());
        } else {
            throw new Exception("not support parameter");
        }
    }

    protected void writeImpl(ChannelHandlerContext ctx, ChannelPromise promise, long requestId, byte status, GeneratedMessageV3 generatedMsg) throws Exception {
        int protoLength = 0;
        int protoInt = 0;

        if (generatedMsg == null) {
            protoInt = status;
            int finalProtoLen = protoLength + 20;
            ByteBuf byteBuf = this.getByteBuf(finalProtoLen, ctx, requestId, protoInt);
            ByteBuffer byteBuffer = this.getByteBuffer(finalProtoLen, requestId, protoInt);
            long sign = CRCUtil.Generic(byteBuffer.array());
            byteBuf.writeLong(sign);

            super.write(ctx, byteBuf, promise);
        } else {
            protoInt = protoHandlerMgr.getProtoEnumInt(generatedMsg.getClass());
            protoLength = generatedMsg.getSerializedSize();
            int finalProtoLen = protoLength + 20;

            ByteBuf byteBuf = this.getByteBuf(finalProtoLen, ctx, requestId, protoInt);
            ByteBuffer byteBuffer = this.getByteBuffer(finalProtoLen, requestId, protoInt);

            byteBuffer.put(generatedMsg.toByteArray());
            long sign = CRCUtil.Generic(byteBuffer.array());

            byteBuf.writeLong(sign);

            try (ByteBufOutputStream out = new ByteBufOutputStream(byteBuf)) {
                generatedMsg.writeTo(out);
                super.write(ctx, out.buffer(), promise);
            }
        }
    }

    private ByteBuffer getByteBuffer(int finalProtoLen, long requestId, int protoInt) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(finalProtoLen - 8);
        byteBuffer.putLong(requestId);
        byteBuffer.putInt(protoInt);
        return byteBuffer;
    }

    private ByteBuf getByteBuf(int finalProtoLen, ChannelHandlerContext ctx, long requestId, int protoInt) {
        ByteBuf byteBuf = ctx.alloc().directBuffer(finalProtoLen + 4, finalProtoLen + 4);
        byteBuf.writeInt(finalProtoLen);
        byteBuf.writeLong(requestId);
        byteBuf.writeInt(protoInt);

        return byteBuf;
    }
}
