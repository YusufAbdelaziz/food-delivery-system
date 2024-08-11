package com.joe.abdelaziz.food_delivery_system.security.role;

import static com.joe.abdelaziz.food_delivery_system.security.permission.Permission.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.joe.abdelaziz.food_delivery_system.security.permission.Permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RoleType {
  USER(Set.of(
      CUSTOMER_READ,
      CUSTOMER_UPDATE,
      CUSTOMER_CREATE,
      CUSTOMER_DELETE)),
  ADMIN(Set.of(
      ADMIN_READ,
      ADMIN_UPDATE,
      ADMIN_CREATE,
      ADMIN_DELETE)),
  COURIER(Set.of(
      COURIER_READ,
      COURIER_UPDATE,
      COURIER_CREATE,
      COURIER_DELETE));

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
        .stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}