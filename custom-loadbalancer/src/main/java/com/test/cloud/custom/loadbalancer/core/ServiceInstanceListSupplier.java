package com.test.cloud.custom.loadbalancer.core;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.Request;

public interface ServiceInstanceListSupplier extends Supplier<List<ServiceInstance>> {

    default List<ServiceInstance> get(Request request) {
        return get();
    }
}
