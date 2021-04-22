package com.test.cloud.openfeign.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.blocking.client.BlockingLoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class OpenfeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenfeignConsumerApplication.class, args);
    }

    /**
     * 强制使用 BlockingLoadBalancerClient 实例，也可以使用ExtendRibbonLoadBalancerClient
     */
    @Bean
    public LoadBalancerClient blockingLoadBalancerClient(LoadBalancerClientFactory loadBalancerClientFactory,
                                                         LoadBalancerProperties properties) {
        return new BlockingLoadBalancerClient(loadBalancerClientFactory, properties);
    }
}
