package com.test.cloud.netflix.ribbon.demo01.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Demo01ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo01ProviderApplication.class, args);
    }
}
