package com.example.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
  boolean existsByName(String name);
}
