package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.orders.orderItem.OrderItemDTO;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFeeDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.utiles.validation.NullableRating;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderRestaurantDTO {

  private Long id;

  @JsonIgnoreProperties({ "cuisines", "menu" })
  private RestaurantDTO existingRestaurant;

  private RestaurantDeliveryFeeDTO deliveryFee;

  private Set<OrderItemDTO> items = new HashSet<>();

  private BigDecimal totalRestaurantReceipt;

  private BigDecimal discountedTotalRestaurantReceipt;

  @NullableRating
  private Byte restaurantRating;

  @Size(max = 2000, message = "Restaurant feedback should be at least 2000 characters in length")
  private String restaurantFeedback;

}
