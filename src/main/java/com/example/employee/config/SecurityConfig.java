package com.example.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.employee.auth.UserAccountRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  // 1) How we hash passwords
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 2) How Spring loads users from our UserAccount table
  @Bean
  public UserDetailsService userDetailsService(UserAccountRepository users) {
    return username -> users.findByUsername(username)
        .map(u -> User.withUsername(u.getUsername())
            .password(u.getPassword())    // already encoded
            .roles(u.getRole())           // "ADMIN" or "USER"
            .build())
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  // 3) AuthenticationManager used in AuthController for login
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  // 4) Main security config: who can access what
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 JwtAuthFilter jwtAuthFilter) throws Exception {
    http
        // We are building a stateless REST API, so disable CSRF + sessions
        .csrf(csrf -> csrf.disable())
        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // Login and auth endpoints: open
            .requestMatchers("/api/auth/**").permitAll()
            // H2 console: open (for dev only)
            .requestMatchers("/h2-console/**").permitAll()
            // READ operations on APIs: USER or ADMIN
            .requestMatchers(HttpMethod.GET,
                "/api/employees/**",
                "/api/departments/**",
                "/api/roles/**"
            ).hasAnyRole("USER", "ADMIN")
            // WRITE operations: only ADMIN
            .requestMatchers(
                "/api/employees/**",
                "/api/departments/**",
                "/api/roles/**"
            ).hasRole("ADMIN")
            // Anything else requires authentication
            .anyRequest().authenticated()
        )
        // Basic auth is enabled mostly for testing; JWT will be primary
        .httpBasic(Customizer.withDefaults());

    // Allow H2 console frames
    http.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

    // Add our JWT filter before the default username/password filter
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
