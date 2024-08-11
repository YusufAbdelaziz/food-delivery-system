package com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee;

import java.math.BigDecimal;
import java.time.Duration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurant;
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
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
// Represents the delivery fee of a restaurant in an order.
public class RestaurantDeliveryFee extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "restaurant_delivery_fee_id")
  private Long id;

  @Column(name = "price")
  private BigDecimal currentPrice;

  @ManyToOne
  @JoinColumn(name = "to_region_id")
  @JsonIgnoreProperties("addresses")
  private Region toRegion;

  @ManyToOne
  @JoinColumn(name = "from_region_id")
  @JsonIgnoreProperties("addresses")
  private Region fromRegion;

  @Column(name = "estimated_duration_seconds")
  @Convert(converter = DurationConverter.class)
  @JsonSerialize(using = CustomDurationSerializer.class)
  @JsonDeserialize(using = CustomDurationDeserializer.class)
  private Duration estimatedDuration;

  @OneToOne
  @JoinColumn(name = "order_restaurant_id", nullable = false)
  @JsonIgnore
  private OrderRestaurant orderRestaurant;

  private BigDecimal discountedPrice;
}
