package com.joe.abdelaziz.food_delivery_system.promotions.promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PromotionDTO {
  private Long id;

  @Size(min = 0, max = 100, message = "Description should be between 0 and 250 characters in length")
  private String description;

  @NotBlank
  @Size(min = 3, max = 100, message = "Promotion code should be between 3 and 100 characters in length")
  private String code;

  @NotNull
  private DiscountType discountType;

  @NotNull
  private BigDecimal discountValue;

  private String headline;

  @JsonIgnoreProperties({ "address", "cuisines", "menu" })
  private RestaurantDTO restaurant;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
  private LocalDateTime startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm a")
  private LocalDateTime endDate;

  private int maxUsers;

  private int usedCount;

  private boolean active;
}
