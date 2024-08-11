package com.joe.abdelaziz.food_delivery_system.promotions.userPromotion;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.Promotion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserPromotionService {

  private final UserPromotionRepository userPromotionRepository;
  private final UserPromotionMapper userPromotionMapper;

  public void assignPromotionToUser(Promotion promotion, Customer customer) {
    UserPromotion newUserPromotion = new UserPromotion();
    newUserPromotion.setCustomer(customer);
    newUserPromotion.setPromotion(promotion);
    userPromotionRepository.save(newUserPromotion);
  }

  public List<UserPromotionDTO> getAll() {
    return userPromotionRepository.findAll()
        .stream()
        .map(up -> userPromotionMapper.toUserPromotionDto(up))
        .toList();
  }
}
