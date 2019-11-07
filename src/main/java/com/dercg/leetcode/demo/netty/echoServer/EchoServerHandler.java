package com.dercg.leetcode.demo.netty.echoServer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // note：该例中没有主动释放msg，是因为在调用write时，msg最后会被netty主动释放
        // write并不会将消息主动发送到网络
        ctx.write(msg);
        // write将要发送的消息缓冲到本地，由flush发送到网络上。
        ctx.flush();

        // 也可以使用 ctx.writeAndFlush(msg)

        ByteBuf newMsg = ctx.alloc().buffer(1024);
        newMsg.writeBytes((ctx.channel().id().asLongText()+"\r\n").getBytes());
        ctx.write(newMsg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
