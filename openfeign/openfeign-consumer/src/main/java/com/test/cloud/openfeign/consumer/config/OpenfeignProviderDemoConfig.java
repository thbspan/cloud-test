package com.test.cloud.openfeign.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import feign.Logger;

public class OpenfeignProviderDemoConfig {

    @Bean
    @Primary
    public Logger.Level feignClientLoggerLevel() {
        return Logger.Level.FULL;
    }
}
