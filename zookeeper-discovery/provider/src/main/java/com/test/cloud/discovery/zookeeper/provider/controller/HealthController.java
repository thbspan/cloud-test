package com.test.cloud.discovery.zookeeper.provider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping("/health")
    public ResponseEntity<Object> health() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
