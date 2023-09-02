package com.CodeCraft.hubbackend.controller;

import org.springframework.security.core.userdetails.User;
import com.CodeCraft.hubbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()

                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

    }

    public AuthenticationResponse login(AuthenticationRequest request) {
    }
}
