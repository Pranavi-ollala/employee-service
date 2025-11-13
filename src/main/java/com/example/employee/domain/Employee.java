package com.example.employee.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


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

  // add these two fields
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "department_id", nullable = false)
  @JsonIgnore 
  private Department department;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  @JsonIgnore 
  private Role role;

  public Employee() {}
  public Employee(String firstName, String lastName, String email, LocalDate dateOfJoining) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.dateOfJoining = dateOfJoining;
  }

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public LocalDate getDateOfJoining() { return dateOfJoining; }
  public void setDateOfJoining(LocalDate dateOfJoining) { this.dateOfJoining = dateOfJoining; }

  public Department getDepartment() { return department; }
  public void setDepartment(Department department) { this.department = department; }
  public Role getRole() { return role; }
  public void setRole(Role role) { this.role = role; }
}
