package com.example.molnbaseradjavaapplikation;


import com.example.molnbaseradjavaapplikation.dto.AuthResponse;
import com.example.molnbaseradjavaapplikation.dto.RegisterRequest;
import com.example.molnbaseradjavaapplikation.repository.UserRepository;
import com.example.molnbaseradjavaapplikation.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    //Test

    @Test
    public void TestRegisterFail(){


    }

    @Test
    public void testLogin() {

    }

    @Test
    public void testLoginFail() {

    }

    @Test
    public void testLogout() {
    }


}
