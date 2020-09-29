package com.test.cloud.openfeign.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.cloud.openfeign.consumer.service.EchoServiceFeignClient;

@RestController
public class ConsumerController {
    @Autowired
    private EchoServiceFeignClient echoServiceFeignClient;

    @GetMapping("/hi")
    public String hello02(String name) {
        // 使用 Feign 调用接口
        String response = echoServiceFeignClient.echo(name);
        // 返回结果
        return "consumer:" + response;
    }
}
