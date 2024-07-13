package com.joe.abdelaziz.food_delivery_system.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/admins")
@AllArgsConstructor
@Validated
@Slf4j
public class AdminController {

  private final AdminService adminService;

  @PostMapping("")
  public ResponseEntity<Admin> createUser(@Valid @RequestBody Admin user) {
    log.info("Request received: " + user);

    return ResponseEntity.ok().body(adminService.insertAdmin(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Admin> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(adminService.findAdminById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<Admin>> getAllUsers() {
    return ResponseEntity.ok().body(adminService.findAll());
  }

}
