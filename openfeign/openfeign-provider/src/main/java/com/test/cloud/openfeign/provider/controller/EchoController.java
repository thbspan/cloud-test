package com.test.cloud.openfeign.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

    @Value("${server.port}")
    private Integer serverPort;

    @GetMapping("/echo")
    public String echo(String name) throws InterruptedException {
        Thread.sleep(100L);
        LOGGER.info("[echo][is called name({})]", name);

        return serverPort + "-provider:" + name;
    }
}
