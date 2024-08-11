package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisineDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CuisineDTO {

  @EqualsAndHashCode.Include
  private Long id;

  @NotBlank
  private String name;

  private String imageUrl;

  @JsonIgnoreProperties({ "cuisine" })
  private Set<RestaurantCuisineDTO> restaurants = new HashSet<>();

}
