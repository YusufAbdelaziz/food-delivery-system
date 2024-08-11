package com.joe.abdelaziz.food_delivery_system.promotions.userPromotion;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.customer.CustomerMapper;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.PromotionMapper;

@Mapper(uses = { CustomerMapper.class, PromotionMapper.class })
public abstract class UserPromotionMapper {

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),

  })
  public abstract UserPromotion toUserPromotion(UserPromotionDTO dto);

  public abstract UserPromotionDTO toUserPromotionDto(UserPromotion promotion);
}
