package com.test.cloud.config.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.cloud.config.user.config.OrderProperties;

@RestController
@RequestMapping("/demo")
@RefreshScope
public class DemoController {
    @Autowired
    private OrderProperties orderProperties;

    @GetMapping("/test01")
    public Object test01() {
        Map<String, Object> result = new HashMap<>(4);
        result.put("createFrequencySeconds", orderProperties.getCreateFrequencySeconds());
        result.put("payTimeoutSeconds", orderProperties.getPayTimeoutSeconds());
        return result;
        // return orderProperties; //为了支持 @RefreshScope 注解，不能直接返回对象
    }

    @Value(value = "${order.pay-timeout-seconds}")
    private Integer payTimeoutSeconds;
    @Value(value = "${order.create-frequency-seconds}")
    private Integer createFrequencySeconds;

    /**
     * 测试 @Value 注解的属性
     */
    @GetMapping("/test02")
    public Map<String, Object> test02() {
        Map<String, Object> result = new HashMap<>(4);
        result.put("payTimeoutSeconds", payTimeoutSeconds);
        result.put("createFrequencySeconds", createFrequencySeconds);
        return result;
    }
}
