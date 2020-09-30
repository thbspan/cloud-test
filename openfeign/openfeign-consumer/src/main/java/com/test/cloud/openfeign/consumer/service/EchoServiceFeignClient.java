package com.test.cloud.openfeign.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.test.cloud.openfeign.provider.api.EchoService;

@FeignClient(name = "openfeign-provider-demo", contextId = "echo-service-feign-client")
public interface EchoServiceFeignClient extends EchoService {

}
