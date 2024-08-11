package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.address.AddressDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.menu.MenuDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisineDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RestaurantDTO {
  @EqualsAndHashCode.Include
  private Long id;

  private String name;

  private String imageUrl;

  private BigDecimal avgRating;

  private int successfulOrders;

  private AddressDTO address;

  private boolean visible;

  @JsonIgnoreProperties({ "restaurant" })
  private Set<RestaurantCuisineDTO> cuisines = new HashSet<>();

  @JsonIgnoreProperties({ "restaurant" })
  private MenuDTO menu;
}
