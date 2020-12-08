package com.dercg.netty.transport.mgr;

import com.dercg.netty.transport.protocol.ProtoHandler;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class ProtoHandlerMgr {
    private Map<Integer, ProtoHandler> protoHandlers = new HashMap<>();
    private Map<Integer, Class<? extends GeneratedMessageV3>> protoEnumMappers = new HashMap<>();
    private Map<Class<? extends GeneratedMessageV3>, Integer> protoMsgMappers = new HashMap<>();

    public void registerProto(int protoNum, Class<? extends GeneratedMessageV3> protoClass) {
        protoEnumMappers.put(protoNum, protoClass);
        protoMsgMappers.put(protoClass, protoNum);
    }

    public void registerProto(Class<? extends GeneratedMessageV3> protoClass) {
        int hashCode = protoClass.getName().hashCode();
        hashCode = hashCode & 0x7FFFFFFF;
        registerProto(hashCode, protoClass);
    }

    public void registerHandler(Class<? extends GeneratedMessageV3> protoClass, ProtoHandler clientProtoHandler) {
        int hashCode = protoClass.getName().hashCode();
        hashCode = hashCode & 0x7FFFFFFF;
        protoHandlers.put(hashCode, clientProtoHandler);
        registerProto(hashCode, protoClass);
    }

    public Class<? extends GeneratedMessageV3> getProtoClass(int protoEnum) {
        return protoEnumMappers.get(protoEnum);
    }

    public int getProtoEnumInt(Class<? extends GeneratedMessageV3> protoClass) {
        return protoMsgMappers.get(protoClass);
    }

    public GeneratedMessageV3 handleClientProto(long requestId, int protoEnum, Channel channel, GeneratedMessageV3 proto) {
        ProtoHandler handler = protoHandlers.get(protoEnum);
        return handler.handle(requestId, channel, proto);
    }
}
