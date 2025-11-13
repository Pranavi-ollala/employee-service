package com.example.employee.web;

import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Simple DTO for error responses
  public static class ApiError {
    private String message;

    public ApiError() {}
    public ApiError(String message) { this.message = message; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
  }

  // 404: entity not found
  @ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
  public ResponseEntity<ApiError> notFound(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError(ex.getMessage()));
  }

  // 404: no endpoint for this URL/method
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiError> noResource(NoResourceFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ApiError("No handler found for this request"));
  }

  // 409: constraint violations (e.g., duplicate email)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ApiError> conflict(DataIntegrityViolationException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(new ApiError("Data conflict (possibly duplicate value)"));
  }

  // 400: bad input / illegal state
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiError> badRequest(IllegalArgumentException ex) {
    return ResponseEntity.badRequest()
        .body(new ApiError(ex.getMessage()));
  }

  // 400: validation errors on @Valid DTOs
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex) {
    String msg = ex.getBindingResult().getFieldErrors().stream()
        .map(f -> f.getField() + " " + f.getDefaultMessage())
        .findFirst()
        .orElse("Validation error");
    return ResponseEntity.badRequest()
        .body(new ApiError(msg));
  }

  // 500: everything else
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> fallback(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ApiError("Unexpected server error"  + ex.getClass().getSimpleName()));
  }
}
