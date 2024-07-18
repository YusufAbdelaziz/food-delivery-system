package com.joe.abdelaziz.food_delivery_system.orders.orderOption;

import java.math.BigDecimal;

import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurant;
import com.joe.abdelaziz.food_delivery_system.orders.orderSpec.OrderSpec;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderOption extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_option_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_spec_id")
  private OrderSpec orderSpec;

  @NotBlank
  private String name;

  @Positive
  private BigDecimal price;

}
