package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.orders.orderItem.OrderItemMapper;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFeeMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;

@Mapper(uses = {
    RestaurantDeliveryFeeMapper.class,
    RestaurantMapper.class,
    OrderItemMapper.class })

public abstract class OrderRestaurantMapper {

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "order", ignore = true),
      @Mapping(target = "name", ignore = true)

  })
  public abstract OrderRestaurant toOrderRestaurant(OrderRestaurantDTO dto);

  public abstract OrderRestaurantDTO toOrderRestaurantDTO(OrderRestaurant orderRestaurant);

}
