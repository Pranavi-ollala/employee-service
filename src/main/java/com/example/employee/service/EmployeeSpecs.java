// src/main/java/com/example/employee/service/EmployeeSpecs.java
package com.example.employee.service;

import org.springframework.data.jpa.domain.Specification;

import com.example.employee.domain.Employee;

public final class EmployeeSpecs {
  private EmployeeSpecs(){}

  public static Specification<Employee> departmentId(Long departmentId) {
    return (root, q, cb) -> departmentId == null ? null :
        cb.equal(root.get("department").get("id"), departmentId);
  }

  public static Specification<Employee> roleId(Long roleId) {
    return (root, q, cb) -> roleId == null ? null :
        cb.equal(root.get("role").get("id"), roleId);
  }

  public static Specification<Employee> q(String qtext) {
    if (qtext == null || qtext.isBlank()) return null;
    String like = "%" + qtext.toLowerCase() + "%";
    return (root, q, cb) -> cb.or(
        cb.like(cb.lower(root.get("firstName")), like),
        cb.like(cb.lower(root.get("lastName")), like),
        cb.like(cb.lower(root.get("email")), like)
    );
  }
}
