package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joe.abdelaziz.food_delivery_system.utiles.compositeKeys.RestaurantCuisineId;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/restaurant-cuisines")
@AllArgsConstructor
@Validated
public class RestaurantCuisineController {
  private final RestaurantCuisineService restaurantCuisineService;

  @PostMapping("")
  public ResponseEntity<RestaurantCuisine> assignRestaurantToCuisine(
      @Valid @RequestBody RestaurantCuisine restaurantCuisine) {
    RestaurantCuisine insertedAddress = restaurantCuisineService.insert(restaurantCuisine);

    return ResponseEntity.ok().body(insertedAddress);
  }

  @GetMapping("/{id}")
  public ResponseEntity<RestaurantCuisine> getRestaurantCuisines(@PathVariable RestaurantCuisineId id) {
    return ResponseEntity.ok().body(restaurantCuisineService.getById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<RestaurantCuisine>> getAllRestaurantCuisines() {
    return ResponseEntity.ok().body(restaurantCuisineService.getAll());
  }
}
