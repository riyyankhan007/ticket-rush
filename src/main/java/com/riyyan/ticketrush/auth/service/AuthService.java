package com.riyyan.ticketrush.auth.service;

import com.riyyan.ticketrush.auth.dto.AuthResponse;
import com.riyyan.ticketrush.auth.dto.LoginRequest;
import com.riyyan.ticketrush.auth.dto.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}