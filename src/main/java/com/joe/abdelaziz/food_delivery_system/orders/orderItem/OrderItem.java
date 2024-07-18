package com.joe.abdelaziz.food_delivery_system.orders.orderItem;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_restaurant_id")
  @JsonIgnoreProperties("items")
  private OrderRestaurant orderRestaurant;

  private int quantity;

  private BigDecimal price;

  private String name;

  @OneToMany(mappedBy = "orderItem")
  @JsonIgnoreProperties("item")
  private Set<OrderSpec> orderSpecs = new HashSet<>();
}
