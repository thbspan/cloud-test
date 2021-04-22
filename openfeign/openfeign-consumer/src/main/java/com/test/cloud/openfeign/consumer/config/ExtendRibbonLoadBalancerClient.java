package com.test.cloud.openfeign.consumer.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 修复 {@link RibbonLoadBalancerClient}版本不兼容问题
 */
//@Configuration
public class ExtendRibbonLoadBalancerClient extends RibbonLoadBalancerClient {
    public ExtendRibbonLoadBalancerClient(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        return choose(serviceId, (Object) request);
    }
}
