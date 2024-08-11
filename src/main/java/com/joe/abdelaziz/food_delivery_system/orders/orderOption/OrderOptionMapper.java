package com.joe.abdelaziz.food_delivery_system.orders.orderOption;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class OrderOptionMapper {
  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),

  })
  public abstract OrderOption toOrderOption(OrderOptionDTO dto);

  public abstract OrderOptionDTO toOrderOptionDTO(OrderOption orderRestaurant);
}
