package com.blog_rest_api.springbootrestapi.service;

import com.blog_rest_api.springbootrestapi.payload.LoginDto;
import com.blog_rest_api.springbootrestapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
