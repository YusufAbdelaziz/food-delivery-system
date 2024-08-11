package com.joe.abdelaziz.food_delivery_system.promotions.userPromotion;

import com.joe.abdelaziz.food_delivery_system.customer.OrderCustomerDTO;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.PromotionDTO;

import lombok.Data;

@Data
public class UserPromotionDTO {

  private Long id;

  private OrderCustomerDTO customer;

  private PromotionDTO promotion;
}
