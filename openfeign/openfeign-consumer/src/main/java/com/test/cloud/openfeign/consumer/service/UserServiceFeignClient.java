package com.test.cloud.openfeign.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.cloud.openfeign.provider.api.UserService;
import com.test.cloud.openfeign.provider.dto.UserDTO;

@FeignClient(name = "openfeign-provider-demo", contextId = "user-service-feign-client")
public interface UserServiceFeignClient extends UserService {

    @PostMapping("/get")
    UserDTO get(@SpringQueryMap UserDTO userDTO);

//    @GetMapping("/get")
//    UserDTO get(@RequestParam("username") String username, @RequestParam("password") String password);

//    @GetMapping("/get")
//    UserDTO get(@RequestParam Map<String, Object> params);
}
