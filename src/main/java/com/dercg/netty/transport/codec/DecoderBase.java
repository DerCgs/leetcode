package com.dercg.netty.transport.codec;

import com.dercg.netty.transport.mgr.ProtoHandlerMgr;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Parser;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class DecoderBase extends LengthFieldBasedFrameDecoder {
    protected final ThreadLocal<ProtoParser> THREAD_LOCAL_PARSER = new ThreadLocal<ProtoParser>() {
        /**
         *	重写initialValue以便在为线程创建变量副本时使用
         */
        @Override
        protected ProtoParser initialValue() {
            /**
             * 创建protobuf协议转换类
             */
            return new ProtoParser();
        }

    };

    private ProtoHandlerMgr protoHandlerMgr;

    public DecoderBase(int maxFrameLength, ProtoHandlerMgr protoHandlerMgr) {
        super(maxFrameLength, 0, 4, 0, 0);

        this.protoHandlerMgr = protoHandlerMgr;
    }

    protected GeneratedMessageV3 readFrame(ByteBuf buffer, int protoEnumInt) throws Exception {
        ByteBufInputStream is = new ByteBufInputStream(buffer);
        ProtoParser parserCache = THREAD_LOCAL_PARSER.get();
        Parser<?> parser = parserCache.getParser(protoEnumInt);
        return (GeneratedMessageV3) parser.parseFrom(is);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ChannelConfig config = ctx.channel().config();

        DefaultSocketChannelConfig socketConfig = (DefaultSocketChannelConfig) config;
        socketConfig.setPerformancePreferences(0, 1, 2);

        ctx.fireChannelRegistered();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    protected final class ProtoParser {
        private final Map<Integer, Parser<?>> parserMap = new HashMap<>();

        public Parser<?> getParser(int protoEnumInt) throws Exception {
            Parser<?> parser = parserMap.get(protoEnumInt);

            if (null != parser) {
                return parser;
            }

            Class<?> clazz = protoHandlerMgr.getProtoClass(protoEnumInt);

            Field field = clazz.getField("PARSER");

            Parser<?> reflectParser = (Parser<?>) field.get(clazz);

            parserMap.put(protoEnumInt, reflectParser);
            return reflectParser;

        }
    }
}
