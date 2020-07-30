package com.test.cloud.discovery.zookeeper.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    @GetMapping("/echo")
    public String echo(String name) {
        return "provider:" + name;
    }
}
