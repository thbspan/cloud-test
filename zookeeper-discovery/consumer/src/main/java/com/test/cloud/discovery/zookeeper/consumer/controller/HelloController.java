package com.test.cloud.discovery.zookeeper.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @LoadBalanced
    @Autowired
    private RestTemplate loadBalancedRestTemplate;

    @GetMapping("/hello")
    public String hello(String name) {
        ServiceInstance instance = loadBalancerClient.choose("zookeeper-provider");
        // List<ServiceInstance> instances = discoveryClient.getInstances("demo-provider");
        // instance = instances.size() > 0 ? instances.get(0) : null;
        if (instance == null) {
            throw new IllegalStateException("获取不到实例");
        }
        String targetUrl = instance.getUri() + "/echo?name=" + name;
        String response = restTemplate.getForObject(targetUrl, String.class);
        // 返回结果
        return "consumer:" + response;
    }

    @GetMapping("/hi")
    public String hi(String name) {
        return "hi " + loadBalancedRestTemplate.getForObject("http://zookeeper-provider/echo?name=" + name, String.class);
    }
}
