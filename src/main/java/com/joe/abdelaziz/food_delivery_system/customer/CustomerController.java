package com.joe.abdelaziz.food_delivery_system.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joe.abdelaziz.food_delivery_system.base.AppUser;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Validated
@Slf4j
public class CustomerController {

  private final CustomerService userService;

  @PostMapping("")
  public ResponseEntity<Customer> createUser(@Valid @RequestBody Customer user) {
    log.info("Request received: " + user);

    return ResponseEntity.ok().body(userService.insertUser(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AppUser> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(userService.findUserById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<Customer>> getAllUsers() {
    return ResponseEntity.ok().body(userService.findAll());
  }

}
