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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = { "code", "active" })
})
@Getter
@Setter
public class Promotion extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "promotion_id")
  private Long id;

  @Size(min = 0, max = 100, message = "Description should be between 0 and 250 characters in length")
  private String description;

  @NotBlank
  @Size(min = 3, max = 100, message = "Promotion code should be between 3 and 100 characters in length")
  private String code;

  @Enumerated(EnumType.STRING)
  private DiscountType discountType;

  private BigDecimal discountValue;

  private String headline;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private int maxUsers;

  private int usedCount;

  private boolean active;

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

  public void addOrder(Order order) {
    orders.add(order);
  }

}
