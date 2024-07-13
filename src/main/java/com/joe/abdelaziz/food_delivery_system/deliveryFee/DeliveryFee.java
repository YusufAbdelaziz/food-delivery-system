package com.joe.abdelaziz.food_delivery_system.deliveryFee;

import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.SqlTimeSerializer;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.utiles.serialization.SqlTimeDeserializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "delivery_fee")
@Getter
@Setter
public class DeliveryFee extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "delivery_fee_id")
  private Long id;

  @OneToOne
  @JoinColumn(name = "to_region_id")
  @JsonIgnoreProperties("addresses")
  private Region toRegion;

  @OneToOne
  @JoinColumn(name = "from_region_id")
  @JsonIgnoreProperties("addresses")
  private Region fromRegion;

  private short price;

  @Column(name = "estimated_duration")
  @JsonSerialize(using = SqlTimeSerializer.class)
  @JsonDeserialize(using = SqlTimeDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
  private Time estimatedDuration;
}
