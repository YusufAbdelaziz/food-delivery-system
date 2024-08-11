package com.joe.abdelaziz.food_delivery_system.orders.orderItem;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.orders.orderSpec.OrderSpecDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDTO {
  private Long id;

  private int quantity;

  @Positive
  private BigDecimal price;

  @NotBlank
  private String name;

  private Set<OrderSpecDTO> specs = new HashSet<>();
}
