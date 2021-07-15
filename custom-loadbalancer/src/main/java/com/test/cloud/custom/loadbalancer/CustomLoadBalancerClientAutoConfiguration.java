package com.test.cloud.custom.loadbalancer;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.AsyncLoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.cloud.custom.loadbalancer.client.CustomLoadBalancerClient;
import com.test.cloud.custom.loadbalancer.support.CustomBalancerClientFactory;

@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({LoadBalancerAutoConfiguration.class, AsyncLoadBalancerAutoConfiguration.class})
@ConditionalOnProperty(value = "spring.cloud.loadbalancer.custom.enabled", havingValue = "true", matchIfMissing = true)
public class CustomLoadBalancerClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CustomBalancerClientFactory customBalancerClientFactory() {
        return new CustomBalancerClientFactory();
    }

    @Bean
    @ConditionalOnMissingBean(LoadBalancerClient.class)
    public LoadBalancerClient loadBalancerClient(CustomBalancerClientFactory customBalancerClientFactory,
                                                 LoadBalancerProperties properties) {
        return new CustomLoadBalancerClient(customBalancerClientFactory, properties);
    }

}
