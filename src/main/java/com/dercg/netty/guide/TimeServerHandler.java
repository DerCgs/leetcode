package com.dercg.netty.guide;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    // 1.
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 2.
        final ByteBuf time = ctx.alloc().buffer(4);
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        // 3.
        final ChannelFuture f = ctx.writeAndFlush(time);

        f.addListener(future -> {
            if (f != future) {
                throw new Exception();
            }
            ctx.close();
        });
        // this is the code using a pre-defined listener.
        // f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
