package com.dercg.netty.transport.codec;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public abstract class EncoderBase extends ChannelOutboundHandlerAdapter {
    protected final ThreadLocal<EncoderMrg> localEncoderMrg = new ThreadLocal<EncoderMrg>() {
        @Override
        protected EncoderMrg initialValue() {
            return new EncoderMrg();
        }
    };

    protected final class EncoderMrg {
        protected int get(GeneratedMessageV3 protoObj) {
            return 0;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
