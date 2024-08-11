package com.joe.abdelaziz.food_delivery_system.utiles.customResponses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateResponse {
  private String message;
  private Long id;
  private boolean visible;

}