package com.test.cloud.custom.loadbalancer.support;

import org.springframework.cloud.context.named.NamedContextFactory;

import com.test.cloud.custom.loadbalancer.ILoadBalancer;
import com.test.cloud.custom.loadbalancer.annotation.CustomBalancerClientConfiguration;
import com.test.cloud.custom.loadbalancer.annotation.CustomBalancerClientSpecification;

public class CustomBalancerClientFactory extends NamedContextFactory<CustomBalancerClientSpecification> {
    /**
     * Property source name for load balancer.
     */
    public static final String NAMESPACE = "customLoadbalancer";

    /**
     * Property for client name within the load balancer namespace.
     */
    public static final String PROPERTY_NAME = NAMESPACE + ".client.name";

    public CustomBalancerClientFactory() {
        super(CustomBalancerClientConfiguration.class, NAMESPACE, PROPERTY_NAME);
    }

    public ILoadBalancer getILoadBalancer(String name) {
        return getInstance(name, ILoadBalancer.class);
    }
}
