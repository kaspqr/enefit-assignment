package com.example.electricity_consumption.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  @Value("${jwt.access.secret}")
  private String accessSecret;

  @Value("${jwt.refresh.secret}")
  private String refreshSecret;

  @Value("${jwt.access.expiration}")
  private long accessExpiration;

  @Value("${jwt.refresh.expiration}")
  private long refreshExpiration;

  private Key getAccessKey() {
    return Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
  }

  private Key getRefreshKey() {
    return Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
  }

  public String generateAccessToken(Long userId, String username, String firstName, String lastName) {
    return Jwts.builder()
            .setSubject(username)
            .claim("userId", userId)
            .claim("username", username)
            .claim("firstName", firstName)
            .claim("lastName", lastName)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
            .signWith(getAccessKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public String generateRefreshToken(String username) {
    return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(getRefreshKey(), SignatureAlgorithm.HS256)
            .compact();
  }

  public String extractUsernameFromToken(String token) {
    try {
      Claims claims = Jwts.parserBuilder()
              .setSigningKey(accessSecret.getBytes())
              .build()
              .parseClaimsJws(token)
              .getBody();
      return claims.getSubject();
    } catch (Exception e) {
      return null;
    }
  }

  public Claims validateAccessToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getAccessKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  public Claims validateRefreshToken(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getRefreshKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
  }
}
