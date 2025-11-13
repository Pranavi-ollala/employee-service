package com.example.employee.auth;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

  private final Key key;
  private final long expiryMs;

  public JwtUtil(
      @Value("${app.jwt.secret}") String secret,
      @Value("${app.jwt.expiryMinutes}") long expiryMinutes
  ) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes());
    this.expiryMs = expiryMinutes * 60 * 1000;
  }

  public String generate(String username, String role) {
    Instant now = Instant.now();
    return Jwts.builder()
        .setSubject(username)
        .claim("role", role)
        .setIssuedAt(Date.from(now))
        .setExpiration(new Date(System.currentTimeMillis() + expiryMs))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();
  }

  public Jws<Claims> parse(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token);
  }
}
