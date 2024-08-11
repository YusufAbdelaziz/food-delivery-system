package com.joe.abdelaziz.food_delivery_system.orders.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.joe.abdelaziz.food_delivery_system.address.AddressMapper;
import com.joe.abdelaziz.food_delivery_system.courier.Courier;
import com.joe.abdelaziz.food_delivery_system.courier.CourierOrderDTO;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerMapper;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantMapper;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.PromotionMapper;
import com.joe.abdelaziz.food_delivery_system.utiles.commonMapper.CourierOrderMapper;

@Mapper(uses = {
    AddressMapper.class,
    OrderRestaurantMapper.class,
    PromotionMapper.class,
    CustomerMapper.class })

public abstract class OrderMapper {

  @Autowired
  private CourierOrderMapper courierOrderMapper;

  @Mappings({
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "activeCustomerAddress", ignore = true),
      @Mapping(target = "courier", expression = "java(mapCourier(dto.getCourier()))")
  })
  public abstract Order toOrder(OrderDTO dto);

  @Mapping(target = "courier", expression = "java(mapCourierOrderDTO(order.getCourier()))")
  public abstract OrderDTO toOrderDTO(Order order);

  protected Courier mapCourier(CourierOrderDTO courierDTO) {
    return courierDTO != null ? courierOrderMapper.toCourier(courierDTO) : null;
  }

  protected CourierOrderDTO mapCourierOrderDTO(Courier courierDTO) {
    return courierDTO != null ? courierOrderMapper.toCourierOrderDTO(courierDTO) : null;
  }

}
