package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cuisine extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cuisine_id")
  private Long id;

  @NotBlank
  private String name;

  private String imageUrl;

  @OneToMany(mappedBy = "cuisine")
  private Set<RestaurantCuisine> restaurants = new HashSet<>();

}
