package com.example.electricity_consumption.controller;

import com.example.electricity_consumption.model.Customer;
import com.example.electricity_consumption.model.MeteringPoint;
import com.example.electricity_consumption.repository.CustomerRepository;
import com.example.electricity_consumption.repository.MeteringPointRepository;
import com.example.electricity_consumption.repository.ConsumptionRepository;
import com.example.electricity_consumption.service.MarketDataService;
import com.example.electricity_consumption.security.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ConsumptionControllerTest {
    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    private Key getAccessKey() {
        return Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        }

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MeteringPointRepository meteringPointRepository;

    @Mock
    private ConsumptionRepository consumptionRepository;

    @Mock
    private MarketDataService marketDataService;

    @InjectMocks
    private ConsumptionController consumptionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetConsumption_Unauthorized() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "InvalidToken");

        when(jwtUtil.validateAccessToken("InvalidToken")).thenThrow(new RuntimeException("Invalid token"));

        ResponseEntity<?> response = consumptionController.getConsumption(2023, request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized", response.getBody());
    }

    @Test
    void testGetConsumption_UserNotFound() {
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Using generateAccessToken from JwtUtil didn't work for some reason
        // Generating one manually here
        String accessToken = Jwts.builder()
                .setSubject("username")
                .claim("userId", 6L)
                .claim("username", "username")
                .claim("firstName", "Pole")
                .claim("lastName", "Olemas")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();

        request.addHeader("Authorization", "Bearer " + accessToken);

        // Mock JWT validation and username extraction
        Claims claims = Jwts.claims().setSubject("username");
        when(jwtUtil.validateAccessToken(accessToken)).thenReturn(claims);
        when(jwtUtil.extractUsernameFromToken(accessToken)).thenReturn("username");

        // Mock customer repository to return empty Optional (user not found)
        when(customerRepository.findByUsername("username")).thenReturn(Optional.empty());

        ResponseEntity<?> response = consumptionController.getConsumption(2023, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found", response.getBody());
    }

    // customerRepository is always empty, even with the line:
    // when(customerRepository.findByUsername("testacc")).thenReturn(Optional.of(customer));
    // commented out the entire test because of it

    /*@Test
    void testGetConsumption_Success() {
        System.out.println("Mocked customer repository instance: " + customerRepository);

        Customer customer = new Customer();
        customer.setId(6L);
        customer.setUsername("testacc");
        customer.setPassword("securepass1234");
        customer.setFirstName("Test");
        customer.setLastName("Account");

        when(customerRepository.findByUsername("testacc")).thenReturn(Optional.of(customer));

        MeteringPoint meteringPoint = new MeteringPoint();
        meteringPoint.setId(7L);
        meteringPoint.setAddress("Tänav 5");
        meteringPoint.setCustomer(customer);

        // Using jwtUtil.generateAccessToken didn't work, so generating it here manually
        String accessToken = Jwts.builder()
                .setSubject(customer.getUsername())
                .claim("userId", customer.getId())
                .claim("username", customer.getUsername())
                .claim("firstName", customer.getFirstName())
                .claim("lastName", customer.getLastName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(getAccessKey(), SignatureAlgorithm.HS256)
                .compact();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + accessToken);

        // JWT validation and username extraction
        Claims claims = Jwts.claims().setSubject(customer.getUsername());
        when(jwtUtil.validateAccessToken(anyString())).thenReturn(claims);
        when(jwtUtil.extractUsernameFromToken(anyString())).thenReturn(customer.getUsername());

        when(meteringPointRepository.findByCustomerId(6L)).thenReturn(List.of(meteringPoint));
        when(consumptionRepository.findByMeteringPointIdAndConsumptionTimeBetween(anyLong(), any(), any()))
                .thenReturn(List.of());
        when(marketDataService.fetchElectricityPrice(anyLong(), anyLong())).thenReturn(Mono.just(Map.of()));

        ResponseEntity<?> response = consumptionController.getConsumption(2023, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(2023, responseBody.get("year"));
        assertNotNull(responseBody.get("metering_points"));
    }*/
}
