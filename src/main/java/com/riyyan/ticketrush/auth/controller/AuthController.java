package com.riyyan.ticketrush.auth.controller;

import com.riyyan.ticketrush.auth.dto.AuthResponse;
import com.riyyan.ticketrush.auth.dto.LoginRequest;
import com.riyyan.ticketrush.auth.dto.SignupRequest;
import com.riyyan.ticketrush.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(
            @Valid @RequestBody SignupRequest request) {

        return authService.signup(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }
}