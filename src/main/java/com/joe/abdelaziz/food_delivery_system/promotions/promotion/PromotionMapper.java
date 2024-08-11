package com.joe.abdelaziz.food_delivery_system.promotions.promotion;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;

@Mapper(uses = { RestaurantMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class PromotionMapper {
  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "orders", ignore = true),
      @Mapping(target = "userPromotions", ignore = true)

  })
  public abstract Promotion toPromotion(PromotionDTO dto);

  @Mapping(target = "restaurant", ignore = true)
  public abstract PromotionDTO toPromotionDto(Promotion promotion);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "userPromotions", ignore = true),
      @Mapping(target = "orders", ignore = true)

  })
  public abstract Promotion toUpdatePromotion(PromotionDTO dto, @MappingTarget Promotion existingPromotion);

  @AfterMapping
  public void mapRestaurant(@MappingTarget PromotionDTO dto, Promotion promotion) {
    RestaurantDTO rDto = new RestaurantDTO();
    Restaurant restaurant = promotion.getRestaurant();
    if (restaurant.getId() != null) {
      rDto.setId(restaurant.getId());
    }
    if (restaurant.getName() != null) {
      rDto.setName(restaurant.getName());
    }

    dto.setRestaurant(rDto);
  }
}
