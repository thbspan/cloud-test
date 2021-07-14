package com.test.cloud.netflix.ribbon.demo01.consumer;

import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class Demo01ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo01ConsumerApplication.class, args);
    }

    /**
     * 使用HttpComponents发送网络请求
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
    }

    @Bean("notLoadBalanceRestTemplate")
    public RestTemplate notLoadBalanceRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
    }
}
