package com.joe.abdelaziz.food_delivery_system.security.auth;

import com.joe.abdelaziz.food_delivery_system.security.role.Role;
import com.joe.abdelaziz.food_delivery_system.utiles.validation.NullableEmail;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank
  @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
  @Size(min = 11)
  private String phoneNumber;

  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  @NullableEmail
  private String email;

  @NotNull
  private Role role;
}