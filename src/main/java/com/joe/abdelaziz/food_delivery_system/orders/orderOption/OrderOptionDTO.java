package com.joe.abdelaziz.food_delivery_system.orders.orderOption;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class OrderOptionDTO {
  private Long id;

  @NotBlank
  private String name;

  @PositiveOrZero
  private BigDecimal price;
}
