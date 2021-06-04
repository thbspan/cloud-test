package com.test.cloud.config.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigUserApplication {
    public static void main(String[] args) {
        // 支持SpringCloud 2020.x.x
        System.setProperty("spring.cloud.bootstrap.enabled", "true");
        SpringApplication.run(ConfigUserApplication.class, args);
    }
}
