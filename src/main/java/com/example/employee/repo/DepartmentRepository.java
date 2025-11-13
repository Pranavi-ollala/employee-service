package com.example.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.domain.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
  boolean existsByName(String name);
}
