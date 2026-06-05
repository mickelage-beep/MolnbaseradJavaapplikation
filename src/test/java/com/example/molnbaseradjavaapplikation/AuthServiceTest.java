package com.example.molnbaseradjavaapplikation;


import com.example.molnbaseradjavaapplikation.dto.AuthResponse;
import com.example.molnbaseradjavaapplikation.dto.LoginRequest;
import com.example.molnbaseradjavaapplikation.dto.RegisterRequest;
import com.example.molnbaseradjavaapplikation.model.Users;
import com.example.molnbaseradjavaapplikation.repository.UserRepository;
import com.example.molnbaseradjavaapplikation.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Micke");
        registerRequest.setPassword("testpassword");

        Mockito.when(userRepository.existsByUsername("Micke"))
                .thenReturn(false);

        Mockito.when(passwordEncoder.encode("testpassword"))
                .thenReturn("hashedPassword");

        AuthResponse authResponse = authService.register(registerRequest);

        assertEquals("User registered successfully", authResponse.getMessage());
        assertEquals("Micke", authResponse.getUsername());

        Mockito.verify(userRepository).save(Mockito.argThat(users ->
                users.getUsername().equals("Micke") &&
                users.getPassword().equals("hashedPassword")));
    }
    //Tester

    @Test
    public void TestRegisterFail(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("Micke");
        registerRequest.setPassword("password");

        Mockito.when(userRepository.existsByUsername("Micke"))
                .thenReturn(true);

        AuthResponse authResponse = authService.register(registerRequest);

        assertEquals("Username already exists", authResponse.getMessage());

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());


    }

    @Test
    public void testLogin() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Micke");
        loginRequest.setPassword("password");

        Users user = new Users("Micke", "hashedPassword");

        Mockito.when(userRepository.findByUsername("Micke"))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("password", "hashedPassword"))
                .thenReturn(true);

        AuthResponse authResponse = authService.login(loginRequest);

        assertEquals("Login successful", authResponse.getMessage());
        assertEquals("Micke", authResponse.getUsername());

    }

    @Test
    public void testLoginFailUsername() {
        LoginRequest request = new LoginRequest();
        request.setUsername("Micke");
        request.setPassword("password");

        Mockito.when(userRepository.findByUsername("Micke"))
                .thenReturn(Optional.empty());

        AuthResponse response = authService.login(request);

        assertEquals("Invalid username or password", response.getMessage());
        assertNull(response.getUsername());

    }

    @Test
    public void testLoginFailPassword() {

        LoginRequest request = new LoginRequest();
        request.setUsername("Micke");
        request.setPassword("password");

        Users user = new Users("Micke", "hashedPassword");

        Mockito.when(userRepository.findByUsername("Micke"))
                .thenReturn(Optional.of(user));

        Mockito.when(passwordEncoder.matches("password", "hashedPassword"))
                .thenReturn(false);

        AuthResponse authResponse = authService.login(request);

        assertEquals("Invalid username or password", authResponse.getMessage());
    }


}
