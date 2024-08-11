package com.joe.abdelaziz.food_delivery_system.security.auth;

import com.joe.abdelaziz.food_delivery_system.security.role.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  private String phoneNumber;
  private String password;
  private Role role;
}