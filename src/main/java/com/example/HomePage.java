package com.example.employee.web;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage{

  @GetMapping("/")
  public Map<String, String> home() {
    return Map.of(
        "app", "employee-service",
        "status", "running",
        "message", "Backend is up. Use /api/auth/login and other /api/... endpoints."
    );
  }
}
