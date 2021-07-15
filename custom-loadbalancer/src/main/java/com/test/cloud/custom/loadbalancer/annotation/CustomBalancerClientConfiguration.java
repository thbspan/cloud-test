package com.test.cloud.custom.loadbalancer.annotation;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.test.cloud.custom.loadbalancer.ILoadBalancer;
import com.test.cloud.custom.loadbalancer.core.DiscoveryClientServiceInstanceListSupplier;
import com.test.cloud.custom.loadbalancer.core.RoundRobinCustomLoadBalancer;
import com.test.cloud.custom.loadbalancer.core.ServiceInstanceListSupplier;

@Configuration(proxyBeanMethods = false)
public class CustomBalancerClientConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer<ServiceInstance> iLoadBalancer(ServiceInstanceListSupplier serviceInstanceListSupplier) {
        return new RoundRobinCustomLoadBalancer(serviceInstanceListSupplier);
    }

    @Bean
    @ConditionalOnMissingBean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(DiscoveryClient discoveryClient, Environment environment) {
        return new DiscoveryClientServiceInstanceListSupplier(discoveryClient, environment);
    }
}
