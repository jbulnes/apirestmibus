package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.controller.dto.AuthCreateUserRequest;
import com.ApiRestMiBus.controller.dto.AuthLoginRequest;
import com.ApiRestMiBus.controller.dto.AuthResponse;
import com.ApiRestMiBus.model.service.impl.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){
        return new ResponseEntity<AuthResponse>(this.userDetailService.createUser(authCreateUser),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<AuthResponse>(userDetailService.loginUser(userRequest), HttpStatus.OK);
    }
}
