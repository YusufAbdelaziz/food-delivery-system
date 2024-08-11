package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRestaurantService {

  private final OrderRestaurantRepository orderRestaurantRepository;

  public List<OrderRestaurantStatsDTO> findRestaurantsAvgRating() {

    return orderRestaurantRepository.findRestaurantsAvgRating();
  }
}
