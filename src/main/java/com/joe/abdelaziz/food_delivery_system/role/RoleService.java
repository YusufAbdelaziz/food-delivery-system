package com.joe.abdelaziz.food_delivery_system.role;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

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

  public Role findRoleByType(RoleType type) {
    return roleRepository.findByType(type)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Role of type %s is not found", type.toString())));
  }

  public List<Role> getAllRoles() {
    return roleRepository.findAll();
  }

}