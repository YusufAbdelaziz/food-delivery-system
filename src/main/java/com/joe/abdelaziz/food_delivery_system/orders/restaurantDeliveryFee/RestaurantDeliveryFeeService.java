package com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.region.RegionMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee.DeliveryFee;
import com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee.DeliveryFeeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RestaurantDeliveryFeeService {

  private final RegionMapper regionMapper;
  private final RestaurantDeliveryFeeMapper orderDeliveryFeeMapper;
  private final DeliveryFeeService deliveryFeeService;

  public RestaurantDeliveryFeeDTO calculateRestaurantDeliveryFee(OrderRestaurantDTO orderRestaurant,
      Customer customer) {

    Region toRegion = customer.getActiveAddress().getRegion();
    Region fromRegion = regionMapper.toRegion(orderRestaurant.getExistingRestaurant().getAddress().getRegion());
    DeliveryFee deliveryFee = deliveryFeeService.findDeliveryFeeBetweenTwoRegions(toRegion, fromRegion);
    return orderDeliveryFeeMapper
        .toRestaurantDeliveryFeeDTO(orderDeliveryFeeMapper.toRestaurantDeliveryFee(deliveryFee));

  }

}
