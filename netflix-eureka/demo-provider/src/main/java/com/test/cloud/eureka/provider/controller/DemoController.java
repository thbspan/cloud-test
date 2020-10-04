package com.test.cloud.eureka.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/echo")
    public String echo(String name) {
        return "provider:" + name;
    }
}
