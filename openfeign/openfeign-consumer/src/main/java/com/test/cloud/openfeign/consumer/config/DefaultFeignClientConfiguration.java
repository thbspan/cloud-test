package com.test.cloud.openfeign.consumer.config;

import org.springframework.context.annotation.Bean;

import feign.Logger;

public class DefaultFeignClientConfiguration {

    @Bean
    public Logger.Level defaultFeignClientLoggerLevel() {
        return Logger.Level.BASIC;
    }
}
