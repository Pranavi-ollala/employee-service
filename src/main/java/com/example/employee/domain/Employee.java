package com.example.employee.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String email;

  private LocalDate dateOfJoining;

  public Employee() {}

  public Employee(String firstName, String lastName, String email, LocalDate dateOfJoining) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.dateOfJoining = dateOfJoining;
  }

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
