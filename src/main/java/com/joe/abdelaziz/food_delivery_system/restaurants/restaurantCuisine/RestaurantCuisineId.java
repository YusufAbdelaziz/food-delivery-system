package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantCuisineId implements Serializable {

  private Long restaurantId;
  private Long cuisineId;

}