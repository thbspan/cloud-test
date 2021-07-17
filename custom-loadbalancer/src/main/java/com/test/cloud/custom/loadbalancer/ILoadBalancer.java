package com.test.cloud.custom.loadbalancer;

import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;

public interface ILoadBalancer<T> {
    @SuppressWarnings("rawtypes")
    Response<T> choose(Request request);
}
