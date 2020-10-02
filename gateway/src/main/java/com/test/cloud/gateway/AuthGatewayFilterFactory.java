package com.test.cloud.gateway;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;

@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    public AuthGatewayFilterFactory() {
        super(AuthGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // 1、token 和 userId 的映射
        Map<String, Integer> tokenMap = new HashMap<>();
        tokenMap.put("jack", 1);

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();

            String token = headers.getFirst(config.getTokenHeaderName());
            if (StringUtils.isEmpty(token)) {
                // 没用token，则不进行认证
                return chain.filter(exchange);
            }
            // 进行认证
            ServerHttpResponse response = exchange.getResponse();
            Integer userId = tokenMap.get(token);

            if (userId == null) {
                // 响应 401 状态码
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                // 响应提示
                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("认证不通过".getBytes());
                return response.writeWith(Flux.just(buffer));
            }
            // 认证通过，添加userId 到Header 中
            request = request.mutate().header(config.getUserIdHeaderName(), String.valueOf(userId)).build();

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    public static class Config {
        private static final String DEFAULT_TOKEN_HEADER_NAME = "token";
        private static final String DEFAULT_HEADER_NAME = "user-id";

        private String tokenHeaderName = DEFAULT_TOKEN_HEADER_NAME;
        private String userIdHeaderName = DEFAULT_HEADER_NAME;

        public String getTokenHeaderName() {
            return tokenHeaderName;
        }

        public String getUserIdHeaderName() {
            return userIdHeaderName;
        }

        public Config setTokenHeaderName(String tokenHeaderName) {
            this.tokenHeaderName = tokenHeaderName;
            return this;
        }

        public Config setUserIdHeaderName(String userIdHeaderName) {
            this.userIdHeaderName = userIdHeaderName;
            return this;
        }
    }
}
