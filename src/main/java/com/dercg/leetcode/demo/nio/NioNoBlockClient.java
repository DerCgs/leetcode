package com.dercg.leetcode.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

public class NioNoBlockClient {
    public static void main(String[] args) throws IOException {
        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
        // 1.1 切换成非阻塞模式
        socketChannel.configureBlocking(false);
        // 1.2获取选择器
        Selector selector = Selector.open();
        // 1.3将通道注册到选择器中，获取服务端返回的数据
        socketChannel.register(selector, SelectionKey.OP_READ);


        // 2. 发送一张图片非服务器端
        String src = ";";
        FileChannel channel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);

        // 创建NIO buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        // 读取本地文件数据，发送到服务器
        while (channel.read(byteBuffer) != -1) {
            // 在读之前切换为读模式
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            // 读完切换为写模式，让通道继续读取文件的数据
            byteBuffer.clear();
        }

        // 轮训地获取选择器上已经就绪的事件，只要select()>0，说明已就绪
        while (selector.select() > 0) {
            // 获取当前选择器所有注册的选择键（已就绪的监听事件）
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            // 获取已就绪的事件，不同的事件做不同的事情
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                // 读事件就绪
                if (selectionKey.isReadable()) {
                    // 得到相应的通道
                    SocketChannel socketChannel1 = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer1 = ByteBuffer.allocate(1024);

                    // 知道服务器端要返回相应的数据给客户端，在此接收
                    int readBytes = socketChannel1.read(byteBuffer1);
                    if (readBytes > 0) {
                        // 切换到读模式
                        byteBuffer1.flip();
                        System.out.println(new String(byteBuffer1.array(), 0, readBytes));
                    }
                }
                iterator.remove();
            }
        }

        // 关闭流
        channel.close();
        socketChannel.close();
    }
}
