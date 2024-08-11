package com.joe.abdelaziz.food_delivery_system.courier;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.orders.order.OrderDTO;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleMapper;
import com.joe.abdelaziz.food_delivery_system.utiles.commonMapper.CourierOrderMapper;

@Mapper(uses = { RoleMapper.class
}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class CourierMapper {

  @Autowired
  private CourierOrderMapper courierOrderMapper;

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "password", ignore = true),
      @Mapping(target = "active", ignore = true),
      @Mapping(target = "avgRating", ignore = true),
      @Mapping(target = "earnings", ignore = true),
      @Mapping(target = "orders", ignore = true),
      @Mapping(target = "role", ignore = true),
      @Mapping(target = "successfulOrders", ignore = true),
      @Mapping(target = "authorities", ignore = true)

  })

  public abstract Courier toCourier(CourierOrderDTO courier);

  public abstract CourierOrderDTO toCourierOrderDTO(Courier courier);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "authorities", ignore = true)

  })

  public abstract Courier toUpdatedCourier(CourierDTO dto, @MappingTarget Courier existingPromotion);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "authorities", ignore = true),
      @Mapping(target = "orders", expression = "java(mapOrders(dto.getOrders()))")
  })
  public abstract Courier toCourier(CourierDTO dto);

  // @Mapping(target = "orders", ignore = true)

  @Mapping(target = "orders", expression = "java(mapOrdersToDTO(courier.getOrders()))")
  public abstract CourierDTO toCourierDTO(Courier courier);

  protected Set<Order> mapOrders(Set<OrderDTO> orderDTOs) {
    if (orderDTOs == null) {
      return null;
    }
    return orderDTOs.stream()
        .map(orderDTO -> courierOrderMapper.toOrderWithoutCourier(orderDTO))
        .collect(Collectors.toSet());
  }

  protected Set<OrderDTO> mapOrdersToDTO(Set<Order> orders) {
    if (orders == null) {
      return null;
    }
    return orders.stream()
        .map(order -> courierOrderMapper.toOrderDTOWithoutCourier(order))
        .collect(Collectors.toSet());
  }

}