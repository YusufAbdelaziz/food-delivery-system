package com.joe.abdelaziz.food_delivery_system.promotions.promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.promotions.userPromotion.UserPromotion;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Promotion extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "promotion_id")
  private Long id;

  private String description;

  private String code;

  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  private BigDecimal discountValue;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private int maxUsers;

  private int usedCount;

  private boolean active;

  @Null
  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  @JsonIgnoreProperties("promotions")
  private Restaurant restaurant;

  @OneToMany(mappedBy = "promotion")
  @JsonIgnoreProperties("promotion")
  private Set<UserPromotion> userPromotions = new HashSet<>();

  @OneToMany(mappedBy = "promotion")
  @JsonIgnoreProperties("promotion")
  private Set<Order> orders = new HashSet<>();

}
