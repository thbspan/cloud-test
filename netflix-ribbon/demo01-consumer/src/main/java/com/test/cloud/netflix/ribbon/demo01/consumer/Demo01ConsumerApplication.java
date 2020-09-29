package com.test.cloud.netflix.ribbon.demo01.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Demo01ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo01ConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("notLoadBalanceRestTemplate")
    public RestTemplate notLoadBalanceRestTemplate() {
        return new RestTemplate();
    }
}
