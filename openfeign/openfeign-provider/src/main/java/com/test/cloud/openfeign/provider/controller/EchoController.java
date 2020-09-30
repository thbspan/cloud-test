package com.test.cloud.openfeign.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.test.cloud.openfeign.provider.api.EchoService;

@RestController
public class EchoController implements EchoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoController.class);

    @Value("${server.port}")
    private Integer serverPort;

    public String echo(String name) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        LOGGER.info("[echo][is called name({})]", name);

        return serverPort + "-provider:" + name;
    }
}
