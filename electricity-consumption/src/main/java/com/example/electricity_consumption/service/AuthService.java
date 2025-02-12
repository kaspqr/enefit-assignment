package com.example.electricity_consumption.service;

import com.example.electricity_consumption.model.Customer;
import com.example.electricity_consumption.repository.CustomerRepository;
import com.example.electricity_consumption.security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final JwtUtil jwtUtil;

    public AuthService(CustomerRepository customerRepository, JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, String> login(String username, String password) {
        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);

        if (optionalCustomer.isEmpty()) {
            throw new RuntimeException("Unauthorized");
        }

        Customer customer = optionalCustomer.get();

        if (!password.equals(customer.getPassword())) {
            throw new RuntimeException("Unauthorized");
        }

        String accessToken = jwtUtil.generateAccessToken(
                customer.getId(),
                customer.getUsername(),
                customer.getFirstName(),
                customer.getLastName()
        );

        String refreshToken = jwtUtil.generateRefreshToken(customer.getUsername());

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            String username = jwtUtil.validateRefreshToken(refreshToken).getSubject();
            Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);

            if (optionalCustomer.isEmpty()) {
                throw new RuntimeException("Unauthorized");
            }

            Customer customer = optionalCustomer.get();
            return jwtUtil.generateAccessToken(
                    customer.getId(),
                    customer.getUsername(),
                    customer.getFirstName(),
                    customer.getLastName()
            );

        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}
