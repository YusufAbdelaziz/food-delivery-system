package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.util.List;

import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantRatingInfo;

public interface OrderRatingInfo {
    Long getId();

    Byte getCourierRating();

    String getCourierFeedback();

    List<OrderRestaurantRatingInfo> getRestaurants();
}