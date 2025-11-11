package com.example.employee.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employee.domain.Employee;
import com.example.employee.repo.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class EmployeeService {

  private final EmployeeRepository repo;

  public EmployeeService(EmployeeRepository repo) {
    this.repo = repo;
  }

  public List<Employee> list() {
    return repo.findAll();
  }

  public Employee get(Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Employee not found: " + id));
  }

  public Employee create(Employee e) {
    if (repo.existsByEmail(e.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }
    return repo.save(e);
  }

  public Employee update(Long id, Employee updated) {
    Employee e = get(id);
    if (!e.getEmail().equals(updated.getEmail()) && repo.existsByEmail(updated.getEmail())) {
      throw new IllegalArgumentException("Email already exists");
    }
    e.setFirstName(updated.getFirstName());
    e.setLastName(updated.getLastName());
    e.setEmail(updated.getEmail());
    e.setDateOfJoining(updated.getDateOfJoining());
    return repo.save(e);
  }

  public void delete(Long id) {
    repo.delete(get(id));
  }
}
