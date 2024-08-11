package com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantDeliveryFeeRepository extends JpaRepository<RestaurantDeliveryFee, Long> {
}
