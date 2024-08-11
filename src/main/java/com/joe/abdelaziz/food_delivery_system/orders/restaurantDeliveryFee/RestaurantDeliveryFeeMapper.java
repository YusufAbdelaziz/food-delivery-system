package com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.joe.abdelaziz.food_delivery_system.region.RegionMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee.DeliveryFee;

@Mapper(uses = { RegionMapper.class })
public abstract class RestaurantDeliveryFeeMapper {

  @Autowired
  RegionMapper regionMapper;

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "orderRestaurant", ignore = true)
  })
  public abstract RestaurantDeliveryFee toRestaurantDeliveryFee(RestaurantDeliveryFeeDTO dto);

  @Mapping(target = "discountedPrice", ignore = true)
  public abstract RestaurantDeliveryFeeDTO toRestaurantDeliveryFeeDTO(RestaurantDeliveryFee orderDeliveryFee);

  @Mappings({ @Mapping(target = "currentPrice", source = "price"),
      // Id should be ignored as we're creating a new OrderDeliveryFee
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "orderRestaurant", ignore = true),
      @Mapping(target = "discountedPrice", ignore = true)

  })

  public abstract RestaurantDeliveryFee toRestaurantDeliveryFee(DeliveryFee deliveryFee);

  // Notice that the delivery fee changes when a promotion is applied.
  @AfterMapping
  protected void afterMappingToDeliveryFee(@MappingTarget RestaurantDeliveryFee deliveryFee,
      RestaurantDeliveryFeeDTO dto) {
    if (dto.getDiscountedPrice() != null) {
      deliveryFee.setCurrentPrice(dto.getDiscountedPrice());
    } else {
      deliveryFee.setCurrentPrice(dto.getCurrentPrice());
    }
  }

}
