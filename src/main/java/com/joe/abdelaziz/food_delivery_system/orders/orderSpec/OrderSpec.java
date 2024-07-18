package com.joe.abdelaziz.food_delivery_system.orders.orderSpec;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.orderItem.OrderItem;
import com.joe.abdelaziz.food_delivery_system.orders.orderOption.OrderOption;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OrderSpec extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_spec_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_item_id")
  @JsonIgnoreProperties("orderSpecs")
  private OrderItem orderItem;

  @OneToMany(mappedBy = "orderSpec", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnoreProperties("orderSpec")
  private Set<OrderOption> options = new HashSet<>();

  @NotBlank
  private String name;
}
