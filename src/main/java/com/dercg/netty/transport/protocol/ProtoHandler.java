package com.dercg.netty.transport.protocol;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

public interface ProtoHandler {
    GeneratedMessageV3 handle(long requestId, Channel channel, GeneratedMessageV3 proto);
}
