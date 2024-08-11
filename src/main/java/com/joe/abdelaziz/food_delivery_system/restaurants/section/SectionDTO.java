package com.joe.abdelaziz.food_delivery_system.restaurants.section;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.restaurants.item.ItemDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SectionDTO {

  private Long id;

  @NotBlank
  @Size(min = 5, message = "Section name should be at least 5 characters in length")
  private String name;

  @JsonIgnoreProperties("section")
  private Set<ItemDTO> items = new HashSet<>();
}
