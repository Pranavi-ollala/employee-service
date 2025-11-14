# Employee Service – Spring Boot Backend (JWT + Role-Based Access)

This is a **Java Spring Boot backend** application that manages:

- Employees  
- Departments  
- Roles  

It exposes REST APIs for CRUD operations and secures them using **JWT authentication** and **role-based authorization**.

This project was built from scratch to learn and practice **backend development** (coming from SAP ABAP background), and to showcase skills for backend developer roles.

---
##  Live Demo

The backend is deployed on Render:

- Base URL: https://employee-service-1-1t74.onrender.com
- Auth login: https://employee-service-1-1t74.onrender.com/api/auth/login
- Example protected API: https://employee-service-1-1t74.onrender.com/api/employees

> Note: All protected endpoints require a JWT token in the `Authorization: Bearer <token>` header.


## 🚦 Quick Start (Test Live API with Postman)

### 1. Login (get JWT token)

- Method: `POST`
- URL: `https://employee-service-1-1t74.onrender.com/api/auth/login`
- Headers:
  - `Content-Type: application/json`
- Body (raw JSON):

```json
{
  "username": "admin",
  "password": "admin123"
}


## ✨ Features

- Java 21 + Spring Boot 3
- RESTful APIs for:
  - Employees
  - Departments
  - Roles
- Relational mapping using **Spring Data JPA** + **H2 in-memory DB**
- Bean validation (`@Valid`, `@NotBlank`, `@Email`, etc.)
- Global exception handling with JSON error responses
- **JWT-based authentication**
  - Login with username/password
  - Get back a JWT token
- **Role-based access control**
  - Roles: `ADMIN`, `USER`
  - USER: read only
  - ADMIN: full CRUD
- Integration tests using **Spring Boot Test + MockMvc**

---

## 🧱 Tech Stack

- **Language:** Java 21  
- **Framework:** Spring Boot 3  
- **Persistence:** Spring Data JPA, H2 in-memory database  
- **Security:** Spring Security 6, JWT (JJWT library)  
- **Build tool:** Maven (with `mvnw` wrapper)  
- **Testing:** JUnit 5, Spring Boot Test, MockMvc  

---

## 🏗️ Architecture Overview

**Layers:**

- `domain/` – JPA entities (Employee, Department, Role, UserAccount)
- `repo/` – Spring Data JPA repositories
- `service/` – Business logic and transactions
- `web/` – REST controllers and DTOs
- `auth/` – Authentication and JWT logic
- `config/` – Security and app configuration

**Security flow:**

1. Client calls `/api/auth/login` with username & password.
2. If credentials are valid, server returns a **JWT token**.
3. Client includes `Authorization: Bearer <token>` header in each protected request.
4. A custom filter (`JwtAuthFilter`) extracts and validates the token on each request.
5. Spring Security checks the user’s role (`ADMIN` / `USER`) and decides if access is allowed.

---

## 🚀 Getting Started

### 1. Prerequisites

- Java 17+ (project is set to Java 21)
- Git
- Maven (optional – wrapper `mvnw` is included)

Check Java:
```bash
java -version


