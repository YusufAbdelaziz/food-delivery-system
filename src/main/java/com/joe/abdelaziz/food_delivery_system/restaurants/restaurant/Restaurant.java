package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurant;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.Promotion;
import com.joe.abdelaziz.food_delivery_system.restaurants.menu.Menu;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisine;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Restaurant extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_id")
  private Long id;

  private String name;

  private String imageUrl;

  private BigDecimal avgRating;

  private int successfulOrders;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @NotNull
  @Valid
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "address_id")
  @JsonIgnoreProperties({ "restaurant", "user" })
  private Address address;

  @OneToMany(mappedBy = "restaurant")
  @JsonIgnoreProperties("restaurant")
  @JsonIgnore
  private Set<OrderRestaurant> orderRestaurants = new HashSet<>();

  @OneToMany(mappedBy = "restaurant")
  @JsonIgnoreProperties("restaurant")
  private Set<RestaurantCuisine> cuisines = new HashSet<>();

  @OneToMany(mappedBy = "restaurant")
  @JsonIgnoreProperties("restaurant")
  @JsonIgnore
  private Set<Promotion> promotions = new HashSet<>();
}
