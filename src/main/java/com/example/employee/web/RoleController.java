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

import com.example.employee.domain.Role;
import com.example.employee.repo.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

  private final RoleRepository repo;

  public RoleController(RoleRepository repo) {
    this.repo = repo;
  }

  // Create role
  @PostMapping
  public ResponseEntity<Role> create(@RequestBody Role r) {
    Role saved = repo.save(r);
    return ResponseEntity.created(URI.create("/api/roles/" + saved.getId())).body(saved);
  }

  // List all roles
  @GetMapping
  public List<Role> list() {
    return repo.findAll();
  }

  // Get one role by id
  @GetMapping("/{id}")
  public Role get(@PathVariable("id") Long id) {
    return repo.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
  }
}
