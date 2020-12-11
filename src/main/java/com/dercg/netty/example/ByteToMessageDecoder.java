package com.dercg.netty.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ByteToMessageDecoder extends ChannelInboundHandlerAdapter {
    // 使用内存拷贝，通过合并收到的ByteBuf到一个ByteBuf
    public static final Cumulator MERGE_CUMULATOR = new Cumulator() {
        @Override
        public ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in) {

            return null;
        }
    };

    public interface Cumulator {
        // 积累收到的字节并返回持有的积累的字节，正确处理收到字节生命周期是实现代码的职责，
        // 所以在完全消费完ByteBuf后要调用ByteBuf 的 release方法
        ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in);
    }
}