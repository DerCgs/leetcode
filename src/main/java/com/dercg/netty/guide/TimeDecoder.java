package com.dercg.netty.guide;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

// ByteToMessageDecoder is an implementation of ChannelInboundHandler which makes it easy to deal with the fragmentation issue.
public class TimeDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        // if decode() adds an object to out，it means the decoder decoded a message successfully.
        // ByteToMessageDecoder will discard the read part of the cumulative buffer.
        out.add(in.readBytes(4));
    }
}
