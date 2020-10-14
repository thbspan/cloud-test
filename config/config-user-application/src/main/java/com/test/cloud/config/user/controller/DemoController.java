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
import com.test.cloud.config.user.config.PrototypeScopeProperties;

@RestController
@RequestMapping("/demo")
@RefreshScope
public class DemoController {
    @Autowired
    private OrderProperties orderProperties;

    @Autowired
    private PrototypeScopeProperties prototypeScopeProperties;

    @GetMapping("/test01")
    public Object test01() {
//        Map<String, Object> result = new HashMap<>(4);
//        result.put("createFrequencySeconds", orderProperties.getCreateFrequencySeconds());
//        result.put("payTimeoutSeconds", orderProperties.getPayTimeoutSeconds());
//        return result;
        System.out.println(prototypeScopeProperties);
        return orderProperties;
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
