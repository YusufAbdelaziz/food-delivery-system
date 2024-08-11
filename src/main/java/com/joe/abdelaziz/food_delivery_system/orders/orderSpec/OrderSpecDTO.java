package com.joe.abdelaziz.food_delivery_system.orders.orderSpec;

import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.orders.orderOption.OrderOptionDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderSpecDTO {

  private Long id;

  private Set<OrderOptionDTO> options = new HashSet<>();

  @NotBlank
  private String name;
}
