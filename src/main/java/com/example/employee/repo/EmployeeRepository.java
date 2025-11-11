package com.example.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employee.domain.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
  boolean existsByEmail(String email);
}
