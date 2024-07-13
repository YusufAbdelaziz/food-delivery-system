package com.joe.abdelaziz.food_delivery_system.courier;

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
@RequestMapping("/api/v1/couriers")
@AllArgsConstructor
@Validated
@Slf4j
public class CourierController {

  private final CourierService courierService;

  @PostMapping("")
  public ResponseEntity<Courier> createUser(@Valid @RequestBody Courier user) {
    log.info("Request received: " + user);

    return ResponseEntity.ok().body(courierService.insertCourier(user));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Courier> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(courierService.findCourierById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<Courier>> getAllUsers() {
    return ResponseEntity.ok().body(courierService.findAll());
  }

}
