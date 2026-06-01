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
            return new AuthResponse("Username already exists", null);
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
                .orElse(null);

        if (user == null) {
            return new AuthResponse("Invalid username or password", null);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("Invalid username or password", null);
        }

        return new AuthResponse("Login successful", user.getUsername());
    }
}