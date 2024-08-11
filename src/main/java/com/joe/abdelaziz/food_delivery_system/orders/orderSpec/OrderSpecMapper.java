package com.joe.abdelaziz.food_delivery_system.orders.orderSpec;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.orders.orderOption.OrderOptionMapper;

@Mapper(uses = { OrderOptionMapper.class })
public abstract class OrderSpecMapper {

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)

  })
  public abstract OrderSpec toOrderSpec(OrderSpecDTO dto);

  public abstract OrderSpecDTO toOrderSpecDTO(OrderSpec orderRestaurant);

}
