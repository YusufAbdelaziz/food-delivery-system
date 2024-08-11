package com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec;

import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOptionDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SpecDTO {
  private Long id;

  private SpecType type;

  @NotBlank(message = "Spec name should not be blank")
  private String name;

  private Set<SpecOptionDTO> options = new HashSet<>();
}
