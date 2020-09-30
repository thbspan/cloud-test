package com.test.cloud.openfeign.provider.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.cloud.openfeign.provider.dto.UserDTO;

@RequestMapping("/user")
public interface UserService {

    @PostMapping("/get")
    UserDTO get(UserDTO userDTO);

    @PostMapping("/post")
    UserDTO post(@RequestBody UserDTO userDTO);
}
