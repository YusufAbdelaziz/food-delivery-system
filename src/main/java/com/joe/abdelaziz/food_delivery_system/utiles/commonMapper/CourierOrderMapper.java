package com.joe.abdelaziz.food_delivery_system.utiles.commonMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.address.AddressMapper;
import com.joe.abdelaziz.food_delivery_system.courier.Courier;
import com.joe.abdelaziz.food_delivery_system.courier.CourierDTO;
import com.joe.abdelaziz.food_delivery_system.courier.CourierMapper;
import com.joe.abdelaziz.food_delivery_system.courier.CourierOrderDTO;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerMapper;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.orders.order.OrderDTO;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantMapper;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFee;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.PromotionMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleMapper;

@Mapper(uses = {
                RestaurantDeliveryFee.class,
                OrderRestaurantMapper.class,
                CourierMapper.class,
                RoleMapper.class,
                RestaurantMapper.class,
                PromotionMapper.class,
                AddressMapper.class,
                CustomerMapper.class })
public interface CourierOrderMapper {
        @Mappings({
                        @Mapping(target = "courier", ignore = true),
                        @Mapping(target = "discountedOrderTotal", ignore = true),
                        @Mapping(target = "discountedTotalDeliveryFees", ignore = true)
        })
        OrderDTO toOrderDTOWithoutCourier(Order order);

        @Mappings({ @Mapping(target = "createdBy", ignore = true),
                        @Mapping(target = "createdDate", ignore = true),
                        @Mapping(target = "lastModifiedBy", ignore = true),
                        @Mapping(target = "lastModifiedDate", ignore = true),
                        @Mapping(target = "activeCustomerAddress", ignore = true),
                        @Mapping(target = "courier", ignore = true) })
        Order toOrderWithoutCourier(OrderDTO orderDTO);

        @Mapping(target = "orders", ignore = true)
        CourierDTO toCourierDTOWithoutOrders(Courier courier);

        @Mappings({
                        @Mapping(target = "createdBy", ignore = true),
                        @Mapping(target = "createdDate", ignore = true),
                        @Mapping(target = "lastModifiedBy", ignore = true),
                        @Mapping(target = "lastModifiedDate", ignore = true),
                        @Mapping(target = "orders", ignore = true),
                        @Mapping(target = "authorities", ignore = true)

        })
        Courier toCourierWithoutOrders(CourierDTO courierDTO);

        CourierOrderDTO toCourierOrderDTO(Courier courier);

        @Mappings({
                        @Mapping(target = "createdBy", ignore = true),
                        @Mapping(target = "createdDate", ignore = true),
                        @Mapping(target = "lastModifiedBy", ignore = true),
                        @Mapping(target = "lastModifiedDate", ignore = true),
                        @Mapping(target = "orders", ignore = true),
                        @Mapping(target = "password", ignore = true),
                        @Mapping(target = "active", ignore = true),
                        @Mapping(target = "avgRating", ignore = true),
                        @Mapping(target = "earnings", ignore = true),
                        @Mapping(target = "role", ignore = true),
                        @Mapping(target = "successfulOrders", ignore = true),
                        @Mapping(target = "authorities", ignore = true)

        })
        Courier toCourier(CourierOrderDTO courierDTO);
}