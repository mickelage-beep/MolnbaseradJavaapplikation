package com.example.molnbaseradjavaapplikation.service;

import com.example.molnbaseradjavaapplikation.dto.AuthResponse;
import com.example.molnbaseradjavaapplikation.dto.LoginRequest;
import com.example.molnbaseradjavaapplikation.dto.RegisterRequest;
import com.example.molnbaseradjavaapplikation.model.Users;
import com.example.molnbaseradjavaapplikation.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Users user = new Users(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );

        userRepository.save(user);

        return new AuthResponse("User registered successfully", user.getUsername());
    }

    public AuthResponse login(LoginRequest request) {
        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid username or password");
        }

        return new AuthResponse("Login successful", user.getUsername());
    }
}