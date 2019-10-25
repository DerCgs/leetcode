package com.dercg.leetcode.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioNoBlockServer {
    public static void main(String[] args) throws IOException {
        // 1、获取通道
        ServerSocketChannel server = ServerSocketChannel.open();
        // 2、切换成非阻塞模式
        server.configureBlocking(false);
        // 3、绑定连接
        server.bind(new InetSocketAddress(6666));
        // 4、获取选择器
        Selector selector = Selector.open();
        // 4.1 将通道注册到选择器上，指定接收"监听通道"事件
        server.register(selector, SelectionKey.OP_ACCEPT);
        // 5 轮训的获取选择器上已"就绪"的事件 ---》只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 6 获取当前选择器所有注册的"选择键"（已就绪的监听事件）
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 7 获取已"就绪"的事件，不同的事件做不同的事
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 接收事件就绪
                if (selectionKey.isAcceptable()) {
                    // 8 获取客户端的链接
                    SocketChannel client = server.accept();
                    // 8.1 切换成非阻塞状态
                    client.configureBlocking(false);
                }
            }
        }
    }
}