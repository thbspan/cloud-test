package com.test.cloud.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@Endpoint(id = "demo")
public class DemoEndpoint {
    private int num = 0;

    // GET请求 http://localhost:8080/actuator/demo
    @ReadOperation
    public int read() {
        return num;
    }

    // POST 请求 http://localhost:8080/actuator/demo/99
    // content-type: application/json
    // {"i": 99}
    @WriteOperation
    public int write(@Selector int i) {
        num += i;
        return num;
    }

    @WriteOperation
    public int write2(@RequestBody String i) {
        num += Integer.parseInt(i);
        return num;
    }

    // DELETE请求 http://localhost:8080/actuator/demo/99
    @DeleteOperation
    public int delete(@Selector int i) {
        num -= 1;
        return num;
    }
}
