package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

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

@RestController
@RequestMapping("/api/v1/restaurants")
@AllArgsConstructor
@Validated
public class RestaurantController {

  private final RestaurantService restaurantService;

  @PostMapping("")
  public ResponseEntity<Restaurant> createUser(@Valid @RequestBody Restaurant restaurant) {

    return ResponseEntity.ok().body(restaurantService.insertRestaurant(restaurant));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(restaurantService.getById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<Restaurant>> getAllUsers() {
    return ResponseEntity.ok().body(restaurantService.getAll());
  }

  @GetMapping("/by-cuisine/{cuisineId}")
  public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@PathVariable Long cuisineId) {
    List<Restaurant> restaurants = restaurantService.getRestaurantsByCuisineId(cuisineId);
    return ResponseEntity.ok(restaurants);
  }

}
