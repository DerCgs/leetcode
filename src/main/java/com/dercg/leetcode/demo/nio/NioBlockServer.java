package com.dercg.leetcode.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NioBlockServer {
    public static void main(String[] args) throws IOException {
        String dst = "/Users/changlu/Desktop/server.data";
        // 1获取通道
        ServerSocketChannel server = ServerSocketChannel.open();
        // 2得到文件通道，将客户端传来的图片写到本地项目下(写模式，没有则创建)
        FileChannel outChannel = FileChannel.open(Paths.get(dst), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        // 3绑定链接
        server.bind(new InetSocketAddress(6666));
        // 4获取客户端的连接（阻塞的）
        SocketChannel client = server.accept();
        // 5要使用NIO，有了Channel，还要有buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 6将客户端传过来的图片保存在本地
        while (client.read(byteBuffer) != -1) {
            // 读之前，切换为读模式
            byteBuffer.flip();
            outChannel.write(byteBuffer);
            // 切换完读模式后，让管道继续读取文件数据
            byteBuffer.clear();
        }

        // 此时服务端保存了图片后，要告诉客户端，图片已经上传成功
        byteBuffer.put("img is success".getBytes());
        byteBuffer.flip();
        client.write(byteBuffer);
        byteBuffer.clear();

        // 7关闭通道
        outChannel.close();
        client.close();
        server.close();
    }
}
