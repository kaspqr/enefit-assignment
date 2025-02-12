package com.example.electricity_consumption.controller;

import com.example.electricity_consumption.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "localhost:5173"}, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            throw new RuntimeException("All fields are required");
        }

        Map<String, String> tokens = authService.login(username, password);

        Cookie cookie = new Cookie("jwt", tokens.get("refreshToken"));
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 days
        response.addCookie(cookie);

        return Map.of("accessToken", tokens.get("accessToken"));
    }

    @GetMapping("/refresh")
    public Map<String, String> refresh(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new RuntimeException("Unauthorized");
        }

        String refreshToken = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("jwt")) {
                refreshToken = cookie.getValue();
            }
        }

        if (refreshToken == null) {
            throw new RuntimeException("Unauthorized");
        }

        String newAccessToken = authService.refreshAccessToken(refreshToken);
        return Map.of("accessToken", newAccessToken);
    }

    @PostMapping("/logout")
    public Map<String, String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Expire immediately
        response.addCookie(cookie);

        return Map.of("message", "Logged out successfully");
    }
}
