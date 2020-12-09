package com.dercg.netty.transport.service;

import com.dercg.netty.transport.mgr.Z_ServiceDiscoverMgr;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceProvider;

import java.util.HashMap;
import java.util.Map;

public class ServiceDiscover {
    private Z_ServiceDiscoverMgr serviceDiscoverMgr;

    private volatile Map<String, ServiceProvider<ServiceEntry>> providers = new HashMap<>();

    private ServiceRoute serviceRoute;

    private Object lock = new Object();

    public ServiceDiscover(Z_ServiceDiscoverMgr serviceDiscoverMgr, ServiceRoute serviceRoute) {
        this.serviceRoute = serviceRoute;
        this.serviceDiscoverMgr = serviceDiscoverMgr;
    }

    public ServiceInstance<ServiceEntry> getService(String serviceName, ProviderStrategyType strategyType) throws Exception {
        ServiceProvider<ServiceEntry> provider = this.getProvider(serviceName, strategyType);

        serviceRoute.setProviderStrategyType(strategyType);
        assert provider != null;
        return provider.getInstance();
    }

    private ServiceProvider<ServiceEntry> getProvider(String serviceName, ProviderStrategyType strategyType) throws Exception {
        ServiceProvider<ServiceEntry> provider = providers.get(serviceName);
        if (provider == null) {
            synchronized (lock) {
                provider = providers.get(serviceName);
                if (provider == null) {
                    provider = serviceDiscoverMgr.get().serviceProviderBuilder()
                            .serviceName(serviceName)
                            .providerStrategy(serviceRoute)
                            .build();
                    provider.start();
                    providers.put(serviceName, provider);
                }
            }
        }
        return provider;
    }

}
