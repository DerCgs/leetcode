package com.dercg.leetcode.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioBlockClient {
    public static void main(String[] args) throws IOException {
        String src = "/Users/changlu/Desktop/data.txt";
        // 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));

        // 发送一张图片给服务器端
        FileChannel fileChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);

        // 要使用NIO，有了Channel，不然要有Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 读取本地文件，发送到服务器
        while (fileChannel.read(byteBuffer) != -1) {
            // 在读之前都要切换成读模式
            byteBuffer.flip();
            socketChannel.write(byteBuffer);

            // 读完切换成写模式，能让管道继续读取文件的数据
            byteBuffer.clear();
        }
        // 告诉服务器已经写完数据(不然服务器端将不能关闭连接)
        socketChannel.shutdownOutput();

        // 客户端接收服务端的响应数据
        int len = 0;
        while ((len = socketChannel.read(byteBuffer)) != -1) {
            // 切换读模式
            byteBuffer.flip();
            System.out.println(new String(byteBuffer.array(), 0, len));
            // 切换写模式
            byteBuffer.clear();
        }

        // 关闭流
        fileChannel.close();
        socketChannel.close();
    }
}
