package com.joe.abdelaziz.food_delivery_system.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.courier.CourierService;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
  private final CourierService courierService;
  private final RestaurantService restaurantService;

  @Scheduled(fixedRateString = "${scheduler.update-restaurant-ratings.rate}")
  void updateRestaurantsAvgRating() {
    restaurantService.updateAvgRatingsForAllRestaurants();
  }

  @Scheduled(fixedRateString = "${scheduler.update-courier-ratings.rate}")
  void updateCouriersStats() {
    courierService.updateCouriersStats();
  }

  @Scheduled(fixedRateString = "${scheduler.update-courier-ratings.rate}")
  void updateCourierTotalOrders() {
    courierService.updateCouriersTotalOrders();
  }
}
