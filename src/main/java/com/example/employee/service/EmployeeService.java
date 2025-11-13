// src/main/java/com/example/employee/service/EmployeeService.java
package com.example.employee.service;

import com.example.employee.domain.*;
import com.example.employee.repo.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {
  private final EmployeeRepository employees;
  private final DepartmentRepository departments;
  private final RoleRepository roles;

  public EmployeeService(EmployeeRepository employees, DepartmentRepository departments, RoleRepository roles) {
    this.employees = employees; this.departments = departments; this.roles = roles;
  }

  public Page<Employee> list(Specification<Employee> spec, Pageable pageable) {
    return employees.findAll(spec, pageable);
  }

  public Employee get(Long id) {
    return employees.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
  }

  public Employee create(Employee e, Long departmentId, Long roleId) {
    if (employees.existsByEmail(e.getEmail())) throw new IllegalArgumentException("Email already exists");
    Department d = departments.findById(departmentId)
        .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentId));
    Role r = roles.findById(roleId)
        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
    e.setDepartment(d);
    e.setRole(r);
    return employees.save(e);
  }

  public Employee update(Long id, Employee updated, Long departmentId, Long roleId) {
    Employee e = get(id);
    if (!e.getEmail().equals(updated.getEmail()) && employees.existsByEmail(updated.getEmail()))
      throw new IllegalArgumentException("Email already exists");

    e.setFirstName(updated.getFirstName());
    e.setLastName(updated.getLastName());
    e.setEmail(updated.getEmail());
    e.setDateOfJoining(updated.getDateOfJoining());
    if (departmentId != null) {
      e.setDepartment(departments.findById(departmentId)
          .orElseThrow(() -> new EntityNotFoundException("Department not found: " + departmentId)));
    }
    if (roleId != null) {
      e.setRole(roles.findById(roleId)
          .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId)));
    }
    return employees.save(e);
  }

  public void delete(Long id) { employees.delete(get(id)); }
}
