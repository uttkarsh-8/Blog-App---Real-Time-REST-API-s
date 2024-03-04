package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.LoginDto;
import com.blog_rest_api.springbootrestapi.payload.RegisterDto;
import com.blog_rest_api.springbootrestapi.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // login rest api
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        String loggedIn = authService.login(loginDto);

        return ResponseEntity.ok(loggedIn);
    }
    // register rest api
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String register = authService.register(registerDto);

        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }
}
