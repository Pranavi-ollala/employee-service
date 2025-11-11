package com.example.employee.web;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {
  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @Email @NotBlank
  private String email;

  private LocalDate dateOfJoining;

  public EmployeeDTO() {}

  public Long getId() { return id; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getEmail() { return email; }
  public LocalDate getDateOfJoining() { return dateOfJoining; }

  public void setId(Long id) { this.id = id; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setEmail(String email) { this.email = email; }
  public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }
}
