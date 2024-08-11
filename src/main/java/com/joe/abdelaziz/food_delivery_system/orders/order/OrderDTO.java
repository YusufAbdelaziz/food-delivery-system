package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.courier.CourierOrderDTO;
import com.joe.abdelaziz.food_delivery_system.customer.OrderCustomerDTO;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.PromotionDTO;
import com.joe.abdelaziz.food_delivery_system.utiles.validation.NullableRating;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrderDTO {
  private Long id;

  @NotNull
  private OrderCustomerDTO customer;

  @JsonIgnoreProperties("restaurant.avgRating")
  private PromotionDTO promotion;

  @NotNull
  private Set<OrderRestaurantDTO> restaurants = new HashSet<>();

  private CourierOrderDTO courier;

  private BigDecimal totalDeliveryFees = BigDecimal.ZERO;

  private BigDecimal discountedTotalDeliveryFees;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
  private LocalDateTime estimatedDeliveryDate;

  private BigDecimal orderTotal = BigDecimal.ZERO;

  private BigDecimal discountedOrderTotal;

  @NullableRating
  private Byte courierRating;

  @Size(max = 2000, message = "Courier feedback should be at least 2000 characters in length")
  private String courierFeedback;

  private OrderStatus status;

}
