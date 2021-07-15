package com.test.cloud.custom.loadbalancer.core;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;

import com.test.cloud.custom.loadbalancer.ILoadBalancer;

public class RoundRobinCustomLoadBalancer implements ILoadBalancer<ServiceInstance> {
    private final AtomicInteger position;
    private final ServiceInstanceListSupplier serviceInstanceListSupplier;

    public RoundRobinCustomLoadBalancer(ServiceInstanceListSupplier serviceInstanceListSupplier) {
        this.position = new AtomicInteger(new Random().nextInt(1000));
        this.serviceInstanceListSupplier = serviceInstanceListSupplier;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Response<ServiceInstance> choose(Request request) {
        List<ServiceInstance> serviceInstances = serviceInstanceListSupplier.get(request);
        return getInstanceResponse(serviceInstances);
    }

    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances) {
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }
        int pos = Math.abs(this.position.incrementAndGet());

        ServiceInstance instance = instances.get(pos % instances.size());

        return new DefaultResponse(instance);
    }
}
