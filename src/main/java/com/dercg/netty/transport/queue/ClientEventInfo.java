package com.dercg.netty.transport.queue;

import com.dercg.netty.transport.service.ProviderStrategyType;
import com.google.protobuf.GeneratedMessageV3;

public class ClientEventInfo {
    private long id;
    private GeneratedMessageV3 body;
    private String serviceName;
    private ProviderStrategyType strategyType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public GeneratedMessageV3 getBody() {
        return body;
    }

    public void setBody(GeneratedMessageV3 body) {
        this.body = body;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ProviderStrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(ProviderStrategyType strategyType) {
        this.strategyType = strategyType;
    }
}
