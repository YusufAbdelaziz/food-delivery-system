package com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee;

import java.math.BigDecimal;
import java.time.Duration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joe.abdelaziz.food_delivery_system.region.RegionDTO;
import com.joe.abdelaziz.food_delivery_system.utiles.serialization.CustomDurationDeserializer;
import com.joe.abdelaziz.food_delivery_system.utiles.serialization.CustomDurationSerializer;

import lombok.Data;

@Data
public class DeliveryFeeDTO {
  private Long id;

  private RegionDTO toRegion;

  private RegionDTO fromRegion;

  private BigDecimal price;

  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;
}
