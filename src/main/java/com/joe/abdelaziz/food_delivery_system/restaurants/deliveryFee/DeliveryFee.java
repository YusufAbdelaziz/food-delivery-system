package com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee;

import java.math.BigDecimal;
import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.utiles.converters.DurationConverter;
import com.joe.abdelaziz.food_delivery_system.utiles.serialization.CustomDurationDeserializer;
import com.joe.abdelaziz.food_delivery_system.utiles.serialization.CustomDurationSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery_fee", uniqueConstraints = @UniqueConstraint(columnNames = { "to_region_id", "from_region_id" }))
@Getter
@Setter
public class DeliveryFee extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_fee_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "to_region_id")
  @JsonIgnoreProperties("addresses")
  private Region toRegion;

  @ManyToOne
  @JoinColumn(name = "from_region_id")
  @JsonIgnoreProperties("addresses")
  private Region fromRegion;

  private BigDecimal price;

  @Column(name = "estimated_duration_seconds")
  @Convert(converter = DurationConverter.class)
  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;

}
