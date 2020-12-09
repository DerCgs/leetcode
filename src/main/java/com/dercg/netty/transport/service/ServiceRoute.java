package com.dercg.netty.transport.service;

import org.apache.curator.x.discovery.ProviderStrategy;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceProvider;
import org.apache.curator.x.discovery.strategies.RandomStrategy;
import org.apache.curator.x.discovery.strategies.RoundRobinStrategy;

import java.util.HashMap;
import java.util.Map;

public class ServiceRoute implements ProviderStrategy<ServiceEntry> {
    private ProviderStrategyType strategyType;

    private static Map<ProviderStrategyType, ProviderStrategy<ServiceEntry>> strategyMap = new HashMap<>();

    static {
        strategyMap.put(ProviderStrategyType.RANDOM, new RandomStrategy<>());
        strategyMap.put(ProviderStrategyType.ROUNDROBIN, new RoundRobinStrategy<>());
        strategyMap.put(ProviderStrategyType.INTSTICKY, new IntStickyStrategy<>());
    }

    public ServiceRoute() {
        this.strategyType = ProviderStrategyType.ROUNDROBIN;
    }

    public void setProviderStrategyType(ProviderStrategyType strategy) {
        this.strategyType = strategy;
    }

    public void setProviderStrategyType(ProviderStrategyType strategy, int id) {
        this.strategyType = strategy;

        if (strategy == ProviderStrategyType.INTSTICKY) {
            ((IntStickyStrategy<ServiceEntry>) strategyMap.get(strategy)).setId(id);
        }
    }

    @Override
    public ServiceInstance<ServiceEntry> getInstance(InstanceProvider<ServiceEntry> instanceProvider) throws Exception {
        return strategyMap.get(strategyType).getInstance(instanceProvider);
    }
}
