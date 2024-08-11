package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantCuisineDTO {

  @EqualsAndHashCode.Include
  private RestaurantCuisineIdDTO id;

  @JsonIgnoreProperties({ "cuisines", "menu", "address" })
  private RestaurantDTO restaurant;

  @JsonIgnoreProperties("restaurants")
  private CuisineDTO cuisine;

}