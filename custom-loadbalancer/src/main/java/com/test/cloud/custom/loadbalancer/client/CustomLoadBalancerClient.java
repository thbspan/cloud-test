package com.test.cloud.custom.loadbalancer.client;

import java.io.IOException;
import java.net.URI;
import java.util.Set;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.CompletionContext;
import org.springframework.cloud.client.loadbalancer.DefaultRequest;
import org.springframework.cloud.client.loadbalancer.DefaultRequestContext;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycle;
import org.springframework.cloud.client.loadbalancer.LoadBalancerLifecycleValidator;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestAdapter;
import org.springframework.cloud.client.loadbalancer.LoadBalancerUriTools;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.client.loadbalancer.ResponseData;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.ReflectionUtils;

import com.test.cloud.custom.loadbalancer.ILoadBalancer;
import com.test.cloud.custom.loadbalancer.support.CustomBalancerClientFactory;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomLoadBalancerClient implements LoadBalancerClient {

    private final CustomBalancerClientFactory customBalancerClientFactory;
    private final LoadBalancerProperties properties;

    public CustomLoadBalancerClient(CustomBalancerClientFactory customBalancerClientFactory, LoadBalancerProperties properties) {
        this.customBalancerClientFactory = customBalancerClientFactory;
        this.properties = properties;
    }

    @Override
    public <T> T execute(String serviceId, LoadBalancerRequest<T> request) throws IOException {
        LoadBalancerRequestAdapter<T, DefaultRequestContext> lbRequest = new LoadBalancerRequestAdapter<>(request,
                new DefaultRequestContext(request, getHint(serviceId)));
        Set<LoadBalancerLifecycle> supportedLifecycleProcessors = getSupportedLifecycleProcessors(serviceId);
        supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onStart(lbRequest));
        ServiceInstance serviceInstance = choose(serviceId, lbRequest);
        if (serviceInstance == null) {
            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onComplete(
                    new CompletionContext<>(CompletionContext.Status.DISCARD, lbRequest, new EmptyResponse())));
            throw new IllegalStateException("No instances available for " + serviceId);
        }
        return execute(serviceId, serviceInstance, request);
    }

    @Override
    public <T> T execute(String serviceId, ServiceInstance serviceInstance, LoadBalancerRequest<T> request) throws IOException {
        DefaultResponse defaultResponse = new DefaultResponse(serviceInstance);
        Set<LoadBalancerLifecycle> supportedLifecycleProcessors = getSupportedLifecycleProcessors(serviceId);
        Request<T> lbRequest = request instanceof Request ? (Request<T>) request : new DefaultRequest<>();
        supportedLifecycleProcessors
                .forEach(lifecycle -> lifecycle.onStartRequest(lbRequest, new DefaultResponse(serviceInstance)));

        try {
            T response = request.apply(serviceInstance);
            Object clientResponse = getClientResponse(response);
            supportedLifecycleProcessors
                    .forEach(lifecycle -> lifecycle.onComplete(new CompletionContext<>(CompletionContext.Status.SUCCESS,
                            lbRequest, defaultResponse, clientResponse)));
            return response;
        } catch (IOException e) {
            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onComplete(
                    new CompletionContext<>(CompletionContext.Status.FAILED, e, lbRequest, defaultResponse)));
            throw e;
        } catch (Exception e) {
            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onComplete(
                    new CompletionContext<>(CompletionContext.Status.FAILED, e, lbRequest, defaultResponse)));
            ReflectionUtils.rethrowRuntimeException(e);
        }
        return null;
    }

    private <T> Object getClientResponse(T response) {
        ClientHttpResponse clientHttpResponse = null;
        if (response instanceof ClientHttpResponse) {
            clientHttpResponse = (ClientHttpResponse) response;
        }
        if (clientHttpResponse != null) {
            try {
                return new ResponseData(clientHttpResponse, null);
            } catch (IOException ignored) {
            }
        }
        return response;
    }

    @Override
    public URI reconstructURI(ServiceInstance instance, URI original) {
        return LoadBalancerUriTools.reconstructURI(instance, original);
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        return choose(serviceId, new DefaultRequest<DefaultRequestContext>());
    }

    @Override
    public <T> ServiceInstance choose(String serviceId, Request<T> request) {
        ILoadBalancer<ServiceInstance> loadBalancer = customBalancerClientFactory.getILoadBalancer(serviceId);
        if (loadBalancer == null) {
            return null;
        }
        Response<ServiceInstance> response = loadBalancer.choose(request);
        if (response == null) {
            return null;
        }
        return response.getServer();
    }

    private Set<LoadBalancerLifecycle> getSupportedLifecycleProcessors(String serviceId) {
        return LoadBalancerLifecycleValidator.getSupportedLifecycleProcessors(
                customBalancerClientFactory.getInstances(serviceId, LoadBalancerLifecycle.class),
                DefaultRequestContext.class, Object.class, ServiceInstance.class);
    }

    private String getHint(String serviceId) {
        String defaultHint = properties.getHint().getOrDefault("default", "default");
        String hintPropertyValue = properties.getHint().get(serviceId);
        return hintPropertyValue != null ? hintPropertyValue : defaultHint;
    }
}
