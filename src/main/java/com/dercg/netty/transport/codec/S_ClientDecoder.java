package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.dercg.netty.transport.mgr.S_ServerSessionMgr;
import com.dercg.netty.transport.protocol.ProtoType;
import com.dercg.netty.transport.queue.EventInfo;
import com.dercg.netty.transport.queue.EventType;
import com.dercg.netty.transport.queue.NonLockQueue;
import com.dercg.netty.transport.util.CRCUtil;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.nio.ByteBuffer;

public class S_ClientDecoder extends DecoderBase {
    private static final int MAX_SERVER_PACKAGE_LENGTH = 1024 * 128;

    private S_ServerSessionMgr sessionMgr;

    public S_ClientDecoder(S_ServerSessionMgr sessionMgr, ProtoHandlerMgr protoHandlerMgr) {
        super(MAX_SERVER_PACKAGE_LENGTH, protoHandlerMgr);
        this.sessionMgr = sessionMgr;
    }

    @Override
    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        ByteBuf byteBuf = buffer.slice(index, length);
        long requestId = buffer.readLong();
        int protoEnumInt = byteBuf.readInt();
        long sign = byteBuf.readLong();

        if (protoEnumInt == ProtoType.SERVER_MODULE_PING) {
            sessionMgr.pingHandler(ctx.channel(), requestId);
            return Unpooled.EMPTY_BUFFER;
        }

        try {
            GeneratedMessageV3 protoObj = this.readFrame(byteBuf, protoEnumInt);

            ByteBuffer byteBuffer = ByteBuffer.allocate(length - 8);
            byteBuffer.putLong(requestId);
            byteBuffer.putInt(protoEnumInt);
            byteBuffer.put(protoObj.toByteArray());
            long tmpSign = CRCUtil.Generic(byteBuffer.array());

            if (sign != tmpSign) {
                sessionMgr.sendMsg(requestId, StatusCode.SIGNERROR, ctx.channel(), null);
                return Unpooled.EMPTY_BUFFER;
            }

            EventInfo eventInfo = new EventInfo();
            eventInfo.setEventType(EventType.CLIENT_PROTO_COMING);
            eventInfo.setBody(protoObj);
            eventInfo.setRequestId(requestId);
            eventInfo.setChannel(ctx.channel());
            eventInfo.setProtoEnum(protoEnumInt);

            NonLockQueue.publish(eventInfo);
        } catch (Exception e) {
            e.printStackTrace();
            sessionMgr.sendMsg(requestId, StatusCode.EXCEPTION, ctx.channel(), null);
        }
        return Unpooled.EMPTY_BUFFER;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        System.out.println("服务器端 连接关闭"+ctx.channel().id());
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventType(EventType.CLIENT_DISCONNECT);
        eventInfo.setChannel(ctx.channel());

        NonLockQueue.publish(eventInfo);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        EventInfo eventInfo = new EventInfo();
        eventInfo.setEventType(EventType.CLIENT_REGISTER);
        eventInfo.setChannel(ctx.channel());
        NonLockQueue.publish(eventInfo);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("建立连接"+ctx.channel().id());
    }
}
