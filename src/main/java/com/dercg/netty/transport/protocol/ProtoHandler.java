package com.dercg.netty.transport.protocol;

import com.google.protobuf.GeneratedMessage;
import io.netty.channel.Channel;

public interface ProtoHandler {
    GeneratedMessage handle(long requestId, Channel channel, GeneratedMessage proto);
}
