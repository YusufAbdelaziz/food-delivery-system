package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.deliveryFee.DeliveryFee;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.orders.orderItem.OrderItem;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;

import jakarta.persistence.CascadeType;
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
@Table(name = "order_restaurant")
@Getter
@Setter
public class OrderRestaurant extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_restaurant_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private Restaurant restaurant;

  @ManyToOne
  @JoinColumn(name = "delivery_fee_id")
  @JsonIgnoreProperties("orderRestaurants")
  private DeliveryFee deliveryFee;

  @OneToMany(mappedBy = "orderRestaurant", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<OrderItem> items = new HashSet<>();

}
