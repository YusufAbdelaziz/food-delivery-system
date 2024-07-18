package com.joe.abdelaziz.food_delivery_system.promotions.userPromotion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.Promotion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_promotion")
public class UserPromotion extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_promotion_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonIgnoreProperties("userPromotions")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "promotion_Id", nullable = false)
  @JsonIgnoreProperties("userPromotions")
  private Promotion promotion;

}
