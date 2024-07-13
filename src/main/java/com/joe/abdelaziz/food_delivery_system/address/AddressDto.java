package com.joe.abdelaziz.food_delivery_system.address;

import com.joe.abdelaziz.food_delivery_system.customer.UserDto;
import com.joe.abdelaziz.food_delivery_system.region.Region;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AddressDto {
  private Long id;

  private double latitude;

  private double longitude;

  private String description;

  private UserDto userId;

  private Region region;

  @NotNull
  @Positive(message = "You should put a positive number as your building number")
  private short buildingNumber;

  @NotNull
  @Positive(message = "You should put a positive number as your apartment number")
  private short apartmentNumber;

  @NotNull
  private boolean isActive;

}
