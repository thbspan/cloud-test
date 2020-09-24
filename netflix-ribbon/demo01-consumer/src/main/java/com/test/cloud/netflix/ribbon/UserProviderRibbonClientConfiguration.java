package com.test.cloud.netflix.ribbon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;

@Configuration
public class UserProviderRibbonClientConfiguration {

    @Bean
    @Primary
    public IRule ribbonCustomRule() {
        return new RandomRule();
    }
}
