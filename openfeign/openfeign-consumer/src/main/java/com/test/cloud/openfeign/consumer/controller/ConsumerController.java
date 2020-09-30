package com.test.cloud.openfeign.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.cloud.openfeign.consumer.service.DemoProviderFeignClient;
import com.test.cloud.openfeign.consumer.service.EchoServiceFeignClient;
import com.test.cloud.openfeign.consumer.service.UserServiceFeignClient;
import com.test.cloud.openfeign.provider.dto.UserDTO;

@RestController
public class ConsumerController {
    @Autowired
    private EchoServiceFeignClient echoServiceFeignClient;
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;
    @Autowired
    private DemoProviderFeignClient demoProviderFeignClient;
    @GetMapping("/hi")
    public String hello02(String name) {
        // 使用 Feign 调用接口
        String response = echoServiceFeignClient.echo(name);
        // 返回结果
        return "consumer:" + response;
    }

    @GetMapping("/baidu")
    public String hello03(String name) {
        return demoProviderFeignClient.test(name);
    }

    @PostMapping("/user")
    public UserDTO testGetDemo(UserDTO demoDTO) {
        return userServiceFeignClient.get(demoDTO);
    }

    @PostMapping("/user_get")
    public UserDTO testPostDemo(UserDTO demoDTO) {
        return userServiceFeignClient.post(demoDTO);
    }

}
