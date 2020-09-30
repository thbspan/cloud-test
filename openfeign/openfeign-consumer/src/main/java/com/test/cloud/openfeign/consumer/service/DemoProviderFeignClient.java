package com.test.cloud.openfeign.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "baidu", url = "https://www.baidu.com")
public interface DemoProviderFeignClient {

    @GetMapping("/") // 请求首页
    String test(@RequestParam("name") String name);
}
