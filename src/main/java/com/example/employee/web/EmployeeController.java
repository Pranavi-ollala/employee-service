// src/main/java/com/example/employee/web/EmployeeController.java
package com.example.employee.web;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.domain.Employee;
import com.example.employee.service.EmployeeService;
import com.example.employee.service.EmployeeSpecs;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
  private final EmployeeService service;
  public EmployeeController(EmployeeService service) { this.service = service; }

  @GetMapping
  public Page<Employee> list(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,desc") String sort,
      @RequestParam(required = false) Long departmentId,
      @RequestParam(required = false) Long roleId,
      @RequestParam(required = false) String q
  ) {
    Sort s = Sort.by(
        sort.contains(",") ? Sort.Order.by(sort.split(",")[0])
            .with(sort.toLowerCase().endsWith("desc") ? Sort.Direction.DESC : Sort.Direction.ASC)
          : Sort.Order.desc(sort)
    );
    Pageable pageable = PageRequest.of(page, size, s);
    Specification<Employee> spec = Specification.where(EmployeeSpecs.departmentId(departmentId))
        .and(EmployeeSpecs.roleId(roleId))
        .and(EmployeeSpecs.q(q));
    return service.list(spec, pageable);
  }

  @GetMapping("/{id}")
  public Employee get(@PathVariable("id") Long id) { return service.get(id); }

  @PostMapping
  public ResponseEntity<Employee> create(@RequestBody @Valid EmployeeDTO dto) {
    Employee e = new Employee(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getDateOfJoining());
    Employee saved = service.create(e, dto.getDepartmentId(), dto.getRoleId());
    return ResponseEntity.created(URI.create("/api/employees/" + saved.getId())).body(saved);
  }

  @PutMapping("/{id}")
  public Employee update(@PathVariable("id") Long id, @RequestBody @Valid EmployeeDTO dto) {
    Employee e = new Employee(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getDateOfJoining());
    return service.update(id, e, dto.getDepartmentId(), dto.getRoleId());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
