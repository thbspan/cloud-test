package com.test.cloud.netflix.zuul.fallback;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackManager;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.slots.block.BlockException;

@Component
public class CustomBlockFallbackProvider implements ZuulBlockFallbackProvider {
    @PostConstruct
    public void init() {
        ZuulBlockFallbackManager.registerProvider(this);
    }

    public String getRoute() {
        return "*";
    }

    public BlockResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof BlockException) {
            return new BlockResponse(429, "blocked request", route);
        } else {
            return new BlockResponse(500, "system error", route);
        }
    }
}
