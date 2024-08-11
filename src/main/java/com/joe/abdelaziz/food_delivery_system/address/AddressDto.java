package com.joe.abdelaziz.food_delivery_system.address;

import com.joe.abdelaziz.food_delivery_system.region.RegionDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddressDTO {
  private Long id;

  private Double latitude;

  private Double longitude;

  private String description;

  private RegionDTO region;

  @NotNull
  @Positive(message = "You should put a positive number as your building number")
  private Short buildingNumber;

  @NotNull
  @Positive(message = "You should put a positive number as your apartment number")
  private Short apartmentNumber;

  @NotNull
  private Boolean active;

}
