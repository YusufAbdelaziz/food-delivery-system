package com.joe.abdelaziz.food_delivery_system.restaurants.option;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.Spec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Option extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "option_id")
  private Long id;

  @Size(min = 5, message = "Item name should be at least 5 characters in length")
  private String name;

  @Positive
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "spec_id")
  @JsonIgnoreProperties("options")
  private Spec spec;

}
