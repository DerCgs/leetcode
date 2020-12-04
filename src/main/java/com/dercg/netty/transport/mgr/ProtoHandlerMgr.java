package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.protocol.ProtoHandler;
import com.google.protobuf.GeneratedMessage;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class ProtoHandlerMgr {
    private Map<Integer, ProtoHandler> protoHandlers = new HashMap<>();
    private Map<Integer, Class<? extends GeneratedMessage>> protoEnumMappers = new HashMap<>();
    private Map<Class<? extends GeneratedMessage>, Integer> protoMsgMappers = new HashMap<>();

    public void registerProto(int protoNum, Class<? extends GeneratedMessage> protoClass) {
        protoEnumMappers.put(protoNum, protoClass);
        protoMsgMappers.put(protoClass, protoNum);
    }

    public void registerProto(Class<? extends GeneratedMessage> protoClass) {
        int hashCode = protoClass.getName().hashCode();
        hashCode = hashCode & 0x7FFFFFFF;
        registerProto(hashCode, protoClass);
    }

    public void registerHandler(Class<? extends GeneratedMessage> protoClass, ProtoHandler clientProtoHandler) {
        int hashCode = protoClass.getName().hashCode();
        hashCode = hashCode & 0x7FFFFFFF;
        protoHandlers.put(hashCode, clientProtoHandler);
        registerProto(hashCode, protoClass);
    }

    public Class<? extends GeneratedMessage> getProtoClass(int protoEnum) {
        return protoEnumMappers.get(protoEnum);
    }

    public int getProtoEnumInt(Class<? extends GeneratedMessage> protoClass) {
        return protoMsgMappers.get(protoClass);
    }

    public GeneratedMessage handleClientProto(long requestId, int protoEnum, Channel channel, GeneratedMessage proto) {
        ProtoHandler handler = protoHandlers.get(protoEnum);
        return handler.handle(requestId, channel, proto);
    }
}
