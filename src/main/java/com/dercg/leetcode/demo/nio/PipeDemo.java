package com.dercg.leetcode.demo.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class PipeDemo {
    public void demo() throws IOException {
        // 获取管道
        Pipe pipe = Pipe.open();
        // 将缓冲区的数据写入管道
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();
        buffer.put("通过单向管道发送数据".getBytes());
        buffer.flip();
        sinkChannel.write(buffer);

        // 读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer.clear();
        int len = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(), 0, len));

        sourceChannel.close();
        sinkChannel.close();
    }
}
