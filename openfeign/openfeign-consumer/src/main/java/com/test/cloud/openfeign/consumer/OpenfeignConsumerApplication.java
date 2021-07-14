package com.test.cloud.openfeign.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.test.cloud.openfeign.consumer.config.DefaultFeignClientConfiguration;

@EnableFeignClients(defaultConfiguration = DefaultFeignClientConfiguration.class)
@SpringBootApplication
public class OpenfeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenfeignConsumerApplication.class, args);
    }
}
