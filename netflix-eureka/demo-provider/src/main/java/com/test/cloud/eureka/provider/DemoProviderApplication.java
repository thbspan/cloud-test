package com.test.cloud.eureka.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringCloudApplication
public class DemoProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }
}
