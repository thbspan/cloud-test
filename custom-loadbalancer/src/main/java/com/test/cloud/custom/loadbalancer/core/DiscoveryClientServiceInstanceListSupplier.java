package com.test.cloud.custom.loadbalancer.core;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;

import com.test.cloud.custom.loadbalancer.support.CustomBalancerClientFactory;

public class DiscoveryClientServiceInstanceListSupplier implements ServiceInstanceListSupplier {
    private final String serviceId;
    private final DiscoveryClient delegate;

    public DiscoveryClientServiceInstanceListSupplier(DiscoveryClient delegate, Environment environment) {
        this.serviceId = environment.getProperty(CustomBalancerClientFactory.PROPERTY_NAME);
        this.delegate = delegate;
    }

    @Override
    public List<ServiceInstance> get() {
        return delegate.getInstances(serviceId);
    }
}
