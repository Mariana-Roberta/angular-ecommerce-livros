// src/main/java/com/pom/spring_ecommerce_livros/dto/LoginRequest.java

package com.pom.spring_ecommerce_livros.dto;

public class LoginRequest {
  private String email;
  private String password;

  // Getters and Setters
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
