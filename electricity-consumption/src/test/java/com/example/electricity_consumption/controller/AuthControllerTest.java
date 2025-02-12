package com.example.electricity_consumption.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AuthController authController;

    @Test
    void testLogin_Failure_MissingFields() {
        Map<String, String> requestBody = Map.of("username", "testuser");

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authController.login(requestBody, response));

        assertEquals("All fields are required", exception.getMessage());
    }
}
