package com.test.cloud.eureka.consumer.controller;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DemoController {
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/demo")
    public String hello(String name) {
        // 获得服务 `demo-provider` 的一个实例
        ServiceInstance instance;
        if (ThreadLocalRandom.current().nextBoolean()) {
            // 获取服务 `demo-provider` 对应的实例列表
            List<ServiceInstance> instances = discoveryClient.getInstances("demo-provider");
            // 随机选择一个
            int size = instances.size();
            instance = size > 0 ? instances.get(ThreadLocalRandom.current().nextInt(size)) : null;
        } else {
            instance = loadBalancerClient.choose("demo-provider");
        }
        if (instance == null) {
            throw new IllegalStateException("获取不到实例");
        }
        // 发起调用
        String targetUrl = instance.getUri() + "/echo?name=" + name;
        String response = restTemplate.getForObject(targetUrl, String.class);
        // 返回结果
        return "consumer:" + response;
    }
}
