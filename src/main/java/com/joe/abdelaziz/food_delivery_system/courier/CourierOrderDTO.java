package com.joe.abdelaziz.food_delivery_system.courier;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourierOrderDTO {

  @NotNull
  private Long id;

  @NotBlank
  @Size(min = 11)
  @Column(unique = true)
  private String phoneNumber;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

}
