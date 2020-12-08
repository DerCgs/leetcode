package com.dercg.netty.transport.queue;

import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

public class EventInfo {
    private EventType eventType;
    private int protoEnum;
    private Channel channel;
    private long requestId;
    private GeneratedMessageV3 body;

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getProtoEnum() {
        return protoEnum;
    }

    public void setProtoEnum(int protoEnum) {
        this.protoEnum = protoEnum;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public GeneratedMessageV3 getBody() {
        return body;
    }

    public void setBody(GeneratedMessageV3 body) {
        this.body = body;
    }
}
