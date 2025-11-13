// src/main/java/com/example/employee/web/EmployeeDTO.java
package com.example.employee.web;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeDTO {
  private Long id;

  @NotBlank private String firstName;
  @NotBlank private String lastName;
  @Email @NotBlank private String email;

  private LocalDate dateOfJoining;

  @NotNull private Long departmentId;
  @NotNull private Long roleId;

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String s) { this.firstName = s; }
  public String getLastName() { return lastName; }
  public void setLastName(String s) { this.lastName = s; }
  public String getEmail() { return email; }
  public void setEmail(String s) { this.email = s; }
  public LocalDate getDateOfJoining() { return dateOfJoining; }
  public void setDateOfJoining(LocalDate d) { this.dateOfJoining = d; }
  public Long getDepartmentId() { return departmentId; }
  public void setDepartmentId(Long id) { this.departmentId = id; }
  public Long getRoleId() { return roleId; }
  public void setRoleId(Long id) { this.roleId = id; }
}
