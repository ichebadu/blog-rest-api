package com.springbootfashion.blogrestapi.service;

import com.springbootfashion.blogrestapi.payload.LoginDto;
import com.springbootfashion.blogrestapi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
