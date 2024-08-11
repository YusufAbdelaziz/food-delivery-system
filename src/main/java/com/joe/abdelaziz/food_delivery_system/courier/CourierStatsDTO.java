package com.joe.abdelaziz.food_delivery_system.courier;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierStatsDTO {
  private Long courierId;
  private BigDecimal totalEarning;
  private BigDecimal avgRating;
}
