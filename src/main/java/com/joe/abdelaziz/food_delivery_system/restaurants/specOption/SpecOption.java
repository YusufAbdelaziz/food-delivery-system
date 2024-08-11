package com.joe.abdelaziz.food_delivery_system.restaurants.specOption;

import java.math.BigDecimal;

import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class SpecOption extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "spec_option_id")
  private Long id;

  @NotBlank
  @Size(min = 2, message = "Option name should be at least 2 characters in length")
  private String name;

  @PositiveOrZero
  private BigDecimal price;

}
