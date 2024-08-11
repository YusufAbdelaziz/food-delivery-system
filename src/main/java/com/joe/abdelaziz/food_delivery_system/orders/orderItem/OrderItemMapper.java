package com.joe.abdelaziz.food_delivery_system.orders.orderItem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.orders.orderSpec.OrderSpecMapper;

@Mapper(uses = { OrderSpecMapper.class })
public abstract class OrderItemMapper {

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)
  })
  public abstract OrderItem toOrderItem(OrderItemDTO dto);

  public abstract OrderItemDTO toOrderItemDTO(OrderItem orderItem);

}
