package com.dercg.leetcode.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioNoBlockClient {
    public static void main(String[] args) throws IOException {
        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
        // 1.1 切换成非阻塞模式
        socketChannel.configureBlocking(false);
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

        // 关闭流
        channel.close();
        socketChannel.close();
    }
}
