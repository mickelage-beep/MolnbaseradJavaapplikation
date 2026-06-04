package com.example.molnbaseradjavaapplikation.controller;

import com.example.molnbaseradjavaapplikation.dto.AuthResponse;
import com.example.molnbaseradjavaapplikation.dto.LoginRequest;
import com.example.molnbaseradjavaapplikation.dto.RegisterRequest;
import com.example.molnbaseradjavaapplikation.service.AuthService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        if (response.getUsername() == null) {
            return ResponseEntity.status(401).body(response);
        }

        return ResponseEntity.ok(response);
    }
}