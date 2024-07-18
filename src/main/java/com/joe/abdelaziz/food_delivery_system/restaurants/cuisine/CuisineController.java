package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

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

@RestController
@RequestMapping("/api/v1/cuisines")
@AllArgsConstructor
@Validated
public class CuisineController {

  private final CuisineService cuisineService;

  @PostMapping("")
  public ResponseEntity<Cuisine> addAddress(@Valid @RequestBody Cuisine cuisine) {
    Cuisine insertedAddress = cuisineService.insert(cuisine);

    return ResponseEntity.ok().body(insertedAddress);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cuisine> getAddressById(@PathVariable Long id) {
    return ResponseEntity.ok().body(cuisineService.getById(id));
  }
}
