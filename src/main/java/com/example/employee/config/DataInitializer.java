package com.example.employee.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.employee.auth.UserAccount;
import com.example.employee.auth.UserAccountRepository;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner initUsers(UserAccountRepository users,
                              PasswordEncoder encoder) {
    return args -> {
      if (users.findByUsername("admin").isEmpty()) {
        UserAccount admin = new UserAccount(
            "admin",
            encoder.encode("admin123"),
            "ADMIN"
        );
        users.save(admin);
      }

      if (users.findByUsername("user").isEmpty()) {
        UserAccount user = new UserAccount(
            "user",
            encoder.encode("user123"),
            "USER"
        );
        users.save(user);
      }
    };
  }
}
