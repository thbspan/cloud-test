package com.test.cloud.netflix.ribbon.demo01.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @Autowired
    private ServerProperties serverProperties;
    @GetMapping("/echo")
    public String echo(String name) throws InterruptedException {
        // 模拟执行 100ms 时长。方便后续测试请求超时
        Thread.sleep(100L);

        return serverProperties.getPort() + "-provider:" + name;
    }
}
