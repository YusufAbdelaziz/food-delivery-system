package com.joe.abdelaziz.food_delivery_system.security.role;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RoleService {

  private final RoleRepository roleRepository;

  public Optional<Role> findRoleById(Long id) {
    return roleRepository.findById(id);
  }

  public Role insertRole(Role role) {
    log.info("Role Type -> " + role.getType());
    return roleRepository.save(role);
  }

  // If role doesn't exist, create
  public Role findRoleByType(RoleType type) {
    Optional<Role> opRole = roleRepository.findByType(type);
    Role role = new Role();
    role.setType(type);
    if (opRole.isEmpty()) {
      role = insertRole(role);
      return role;
    } else {
      return opRole.get();
    }
  }

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

}