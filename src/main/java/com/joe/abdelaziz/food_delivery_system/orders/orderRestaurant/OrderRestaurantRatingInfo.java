package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public interface OrderRestaurantRatingInfo {

    String getName();

    Long getId();

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    Byte getRestaurantRating();

    @Size(max = 2000, message = "Restaurant feedback should be at least 2000 characters in length")
    String getRestaurantFeedback();

}
