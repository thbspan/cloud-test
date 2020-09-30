package com.test.cloud.openfeign.provider.controller;

import org.springframework.web.bind.annotation.RestController;

import com.test.cloud.openfeign.provider.api.UserService;
import com.test.cloud.openfeign.provider.dto.UserDTO;

@RestController
public class UserController implements UserService {

    @Override
    public UserDTO get(UserDTO userDTO) {
        return userDTO;
    }

    @Override
    public UserDTO post(UserDTO userDTO) {
        return userDTO;
    }
}
