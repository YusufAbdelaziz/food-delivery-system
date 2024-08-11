package com.joe.abdelaziz.food_delivery_system.restaurants.menu;

import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.section.SectionDTO;

import lombok.Data;

@Data
public class MenuDTO {

  private Long id;

  private RestaurantDTO restaurant;

  private Set<SectionDTO> sections = new HashSet<>();
}
