package com.test.cloud.netflix.ribbon.demo01.consumer.controller;

import javax.annotation.Resource;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Resource
    private RestTemplate notLoadBalanceRestTemplate;
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private LoadBalancerClient loadBalancerClient;

    private final String serviceId = "demo01-provider";

    @GetMapping("/hello")
    public String hello(String name) {
        // 获得服务 `serviceId` 的一个实例
        ServiceInstance instance = loadBalancerClient.choose(serviceId);
        // 发起调用
        String targetUrl = instance.getUri() + "/echo?name=" + name;
        String response = notLoadBalanceRestTemplate.getForObject(targetUrl, String.class);
        // 返回结果
        return "consumer:" + response;
    }

    @GetMapping("/hello02")
    public String hello02(String name) {
        // 直接使用 RestTemplate 调用`serviceId`服务
        String targetUrl = "http://" + serviceId + "/echo?name=" + name;
        String response = restTemplate.getForObject(targetUrl, String.class);
        // 返回结果
        return "consumer:" + response;
    }
}
