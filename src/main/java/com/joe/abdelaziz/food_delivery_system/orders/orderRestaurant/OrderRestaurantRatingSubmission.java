package com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRestaurantRatingSubmission(
    Long id,
    @NotNull @Min(1) @Max(5) Byte restaurantRating,
    @Size(max = 2000) String restaurantFeedback) {
}