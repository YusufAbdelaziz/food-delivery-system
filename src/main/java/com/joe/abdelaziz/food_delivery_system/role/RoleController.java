package com.joe.abdelaziz.food_delivery_system.role;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@AllArgsConstructor
public class RoleController {

  private final RoleService roleService;

  @PostMapping("")
  public ResponseEntity<Role> addRole(@RequestBody Role role) {
    Role insertedRole = roleService.insertRole(role);
    return ResponseEntity.ok().body(insertedRole);
  }

  @GetMapping("")
  public ResponseEntity<List<Role>> getAllRoles() {
    return ResponseEntity.ok().body(roleService.getAllRoles());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
    return ResponseEntity.ok().body(roleService.findRoleById(id).get());
  }
}
