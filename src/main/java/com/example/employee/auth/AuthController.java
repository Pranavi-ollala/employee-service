package com.example.employee.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authManager;
  private final JwtUtil jwtUtil;
  private final UserAccountRepository users;
  private final PasswordEncoder encoder;

  public AuthController(AuthenticationManager authManager,
                        JwtUtil jwtUtil,
                        UserAccountRepository users,
                        PasswordEncoder encoder) {
    this.authManager = authManager;
    this.jwtUtil = jwtUtil;
    this.users = users;
    this.encoder = encoder;
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    Authentication auth = authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );

    String username = auth.getName();
    UserAccount user = users.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("User not found after auth"));

    String token = jwtUtil.generate(user.getUsername(), user.getRole());
    return ResponseEntity.ok(new TokenResponse(token, user.getRole()));
  }

  // Simple DTO for token response
  public static class TokenResponse {
    private String token;
    private String role;

    public TokenResponse(String token, String role) {
      this.token = token;
      this.role = role;
    }

    public String getToken() { return token; }
    public String getRole() { return role; }
  }
}
