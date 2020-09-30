package com.test.cloud.openfeign.provider.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface EchoService {

    @GetMapping("/echo")
    String echo(@RequestParam("name") String name);
}
