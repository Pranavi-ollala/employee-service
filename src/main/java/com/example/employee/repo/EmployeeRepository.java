// src/main/java/com/example/employee/repo/EmployeeRepository.java
package com.example.employee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.employee.domain.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
  boolean existsByEmail(String email);
}
