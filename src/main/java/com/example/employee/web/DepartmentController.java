package com.example.employee.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.domain.Department;
import com.example.employee.repo.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

  private final DepartmentRepository repo;

  public DepartmentController(DepartmentRepository repo) {
    this.repo = repo;
  }

  // Create department
  @PostMapping
  public ResponseEntity<Department> create(@RequestBody Department d) {
    Department saved = repo.save(d);
    return ResponseEntity.created(URI.create("/api/departments/" + saved.getId())).body(saved);
  }

  // List all departments
  @GetMapping
  public List<Department> list() {
    return repo.findAll();
  }

  // Get one department by id (optional but useful)
  @GetMapping("/{id}")
  public Department get(@PathVariable("id") Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Department not found: " + id));
  }
}
