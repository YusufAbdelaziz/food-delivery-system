package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.Cuisine;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "restaurant_cuisine")
public class RestaurantCuisine extends BaseEntity {

  @EmbeddedId
  @AttributeOverrides({
      @AttributeOverride(name = "cuisineId", column = @Column(name = "cuisine_id")),
      @AttributeOverride(name = "restaurantId", column = @Column(name = "restaurant_id"))
  })
  private RestaurantCuisineId id;

  @ManyToOne
  @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
  @JsonIgnoreProperties("cuisines")
  private Restaurant restaurant;

  @ManyToOne
  @JoinColumn(name = "cuisine_id", insertable = false, updatable = false)
  @JsonIgnoreProperties("restaurants")
  private Cuisine cuisine;
}
