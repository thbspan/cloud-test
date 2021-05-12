package com.test.cloud.netflix.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy // 开启 Zuul 网关
public class ZuulApplication {

    public static void main(String[] args) {
        // SentinelZuulAutoConfiguration # init 方法已经设置了
        // System.setProperty(SentinelConfig.APP_TYPE_PROP_KEY, ConfigConstants.APP_TYPE_ZUUL_GATEWAY);
        SpringApplication.run(ZuulApplication.class, args);
    }
}
