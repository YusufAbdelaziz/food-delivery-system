package com.joe.abdelaziz.food_delivery_system.region;

import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.address.AddressDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegionDto {
  private Long id;

  @NotBlank
  @Size(max = 100, message = "You should provide a region name of length 100 character at most")
  private String name;

  // @JsonIgnoreProperties("region")
  private Set<AddressDto> addresses;
}
