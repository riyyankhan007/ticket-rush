package com.riyyan.ticketrush.auth.service;

import com.riyyan.ticketrush.auth.dto.AuthResponse;
import com.riyyan.ticketrush.auth.dto.LoginRequest;
import com.riyyan.ticketrush.auth.dto.SignupRequest;
import com.riyyan.ticketrush.auth.jwt.JwtService;
import com.riyyan.ticketrush.auth.security.CustomUserDetails;
import com.riyyan.ticketrush.entity.User;
import com.riyyan.ticketrush.enums.Role;
import com.riyyan.ticketrush.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse signup(SignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setEnabled(true);
        user.setEmailVerified(false);

        userRepository.save(user);

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken("")
                .tokenType("Bearer")
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken("")
                .tokenType("Bearer")
                .build();
    }
}