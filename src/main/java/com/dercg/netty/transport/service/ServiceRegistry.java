package com.dercg.netty.transport.service;

import com.dercg.netty.transport.mgr.Z_ServiceDiscoverMgr;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.UriSpec;

public class ServiceRegistry {
    private final Z_ServiceDiscoverMgr serviceDiscoverMgr;

    public ServiceRegistry(Z_ServiceDiscoverMgr serviceDiscoverMgr) {
        this.serviceDiscoverMgr = serviceDiscoverMgr;
    }

    public void registerService(ServiceEntry serviceEntry) throws Exception {
        ServiceInstance<ServiceEntry> instance = ServiceInstance.<ServiceEntry>builder()
                .id(serviceEntry.address())
                .name(serviceEntry.getServiceName())
                .address(serviceEntry.getIp())
                .port(serviceEntry.getPort())
                .payload(serviceEntry)
                .uriSpec(new UriSpec("{scheme}://{address}:{port}"))
                .build();

        serviceDiscoverMgr.get().registerService(instance);
    }

    public void unregisterService(ServiceEntry serviceEntry) throws Exception {
        ServiceInstance<ServiceEntry> instance = ServiceInstance.<ServiceEntry>builder()
                .id(serviceEntry.address())
                .name(serviceEntry.getServiceName())
                .build();
        serviceDiscoverMgr.get().unregisterService(instance);
    }

    public void close() {
        serviceDiscoverMgr.close();
    }
}
