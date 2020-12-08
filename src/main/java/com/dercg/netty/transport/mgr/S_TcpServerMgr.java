package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.codec.S_ClientDecoder;
import com.dercg.netty.transport.codec.S_ServerEncoder;
import com.dercg.netty.transport.service.EndPoint;
import com.dercg.netty.transport.timer.DefaultTimer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class S_TcpServerMgr {
    private EventGroupMgr eventGroupMgr;
    private S_ServerSessionMgr serverSessionMgr;
    private TimerMgr timerMgr;
    private ProtoHandlerMgr protoHandlerMgr;

    public S_TcpServerMgr(EventGroupMgr eventGroupMgr, S_ServerSessionMgr serverSessionMgr, TimerMgr timerMgr, ProtoHandlerMgr protoHandlerMgr) {
        this.eventGroupMgr = eventGroupMgr;
        this.serverSessionMgr = serverSessionMgr;
        this.protoHandlerMgr = protoHandlerMgr;
        this.timerMgr = timerMgr;
        this.timerMgr.add(new DefaultTimer(this.serverSessionMgr::closeTimeoutSession));
    }

    public S_ServerSessionMgr getServerSessionMgr() {
        return this.serverSessionMgr;
    }

    public EndPoint acceptService(String ip, int port) throws InterruptedException {
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(eventGroupMgr.getBossGroup(), eventGroupMgr.getWorkGroup())
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, 2000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_RCVBUF, 64 * 1024)
                .option(ChannelOption.SO_SNDBUF, 64 * 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new S_ClientDecoder(serverSessionMgr, protoHandlerMgr))
                                .addLast(new S_ServerEncoder(protoHandlerMgr));
                    }
                })
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_LINGER, 0);
        serverBootstrap.bind(port).sync().channel();
        return new EndPoint(ip, port);
    }
}
