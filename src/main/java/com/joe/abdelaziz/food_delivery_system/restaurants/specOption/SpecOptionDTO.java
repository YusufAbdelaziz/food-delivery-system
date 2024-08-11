package com.joe.abdelaziz.food_delivery_system.restaurants.specOption;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpecOptionDTO {

  private Long id;

  @NotBlank
  @Size(min = 2, message = "Option name should be at least 2 characters in length")
  private String name;

  @PositiveOrZero
  private BigDecimal price;

}
