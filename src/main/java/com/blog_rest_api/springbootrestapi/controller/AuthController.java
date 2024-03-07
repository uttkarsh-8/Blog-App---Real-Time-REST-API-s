package com.blog_rest_api.springbootrestapi.controller;

import com.blog_rest_api.springbootrestapi.payload.JwtAuthResponse;
import com.blog_rest_api.springbootrestapi.payload.LoginDto;
import com.blog_rest_api.springbootrestapi.payload.RegisterDto;
import com.blog_rest_api.springbootrestapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "CRUD REST APIs for Authentication"
)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // login rest api
    @Operation(
            summary = "Login With Username Or Email And Get A Bearer Token REST API",
            description = "Used to login with either username or email, and get a bearer token"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }
    // register rest api
    @Operation(
            summary = "Register REST API",
            description = "Used to register"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 SUCCESS"
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String register = authService.register(registerDto);

        return new ResponseEntity<>(register, HttpStatus.CREATED);
    }
}
