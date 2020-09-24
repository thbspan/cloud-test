package com.test.cloud.netflix.ribbon.demo01.consumer.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

import com.test.cloud.netflix.ribbon.DefaultRibbonClientConfiguration;
import com.test.cloud.netflix.ribbon.UserProviderRibbonClientConfiguration;

@Configuration
@RibbonClients(
        // 客户端级别的自定义配置
        value = {@RibbonClient(name = "demo01-provider", configuration = UserProviderRibbonClientConfiguration.class)},
        // 全局配置
        defaultConfiguration = DefaultRibbonClientConfiguration.class
)
public class RibbonConfiguration {

}
