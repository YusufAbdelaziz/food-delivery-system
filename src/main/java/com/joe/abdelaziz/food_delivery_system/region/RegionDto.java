package com.joe.abdelaziz.food_delivery_system.region;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionDTO {
  private Long id;

  @NotBlank
  @Size(max = 100, message = "You should provide a region name of length 100 character at most")
  private String name;

}
