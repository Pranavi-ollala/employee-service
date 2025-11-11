package com.example.employee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

  @Value("${app.name}")
  private String appName;

  @Value("${app.version}")
  private String appVersion;

  @GetMapping("/health")
  public Map<String, String> health() {
    return Map.of("status", "UP");
  }

  @GetMapping("/version")
  public Map<String, String> version() {
    return Map.of("app", appName, "version", appVersion);
  }
}
