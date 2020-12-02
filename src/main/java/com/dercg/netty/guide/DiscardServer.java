package com.dercg.netty.guide;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
    private final int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // 1. NioEventLoopGroup is a multithreaded event loop that handles I/O operation.
        // Netty provides various EventLoopGroup implementations for different kind of transports.
        // In this example, the first one, often called 'boss', accepts an incoming connection.
        // The second one, often called 'work', handles the traffic of the accepted connection once the boss accepts
        // the connection and registers the accepted connection to the worker.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. ServerBootstrap is a helper class that sets up a server.
            // You can set up the server using a Channel directly, but you do not need to do that in most cases.
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    // 3. NioServerSocketChannel is used to instantiate a new Channel to accept incoming connections.
                    .channel(NioServerSocketChannel.class)
                    // 4. The childHandler() will always be evaluated by a newly accepted Channel.
                    // The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel.
                    // Configure the ChannelPipeline of the new Channel by adding some handlers such as DiscardServerHandler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    // 5 option() is for the NioServerSocketChannel that accepts incoming connections.
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 6 childOption() if for the Channels accepted by the parent ServerChannel, which is NioServerSocketChannel in this case.
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            // in this example, this does not happen, but you can do that to gracefully shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }

}
