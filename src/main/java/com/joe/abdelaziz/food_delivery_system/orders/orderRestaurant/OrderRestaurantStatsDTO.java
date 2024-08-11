package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderRestaurantStatsDTO {
  private Long restaurantId;
  private BigDecimal avgRating;

}
