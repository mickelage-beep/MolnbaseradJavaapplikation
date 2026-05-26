package com.example.molnbaseradjavaapplikation.controller;

import com.example.molnbaseradjavaapplikation.dto.AuthResponse;
import com.example.molnbaseradjavaapplikation.dto.LoginRequest;
import com.example.molnbaseradjavaapplikation.dto.RegisterRequest;
import com.example.molnbaseradjavaapplikation.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}