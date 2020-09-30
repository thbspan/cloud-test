package com.test.cloud.openfeign.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.cloud.openfeign.consumer.config.OpenfeignProviderDemoConfig;

@FeignClient(name = "openfeign-provider-demo", configuration = OpenfeignProviderDemoConfig.class)
public interface EchoServiceFeignClient {

    @GetMapping("/echo")
    String echo(@RequestParam("name") String name);
}

