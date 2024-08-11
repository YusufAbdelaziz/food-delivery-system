package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.util.List;

import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantRatingSubmission;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record OrderRatingSubmission(
                @NotNull Long id,
                @NotNull @Min(1) @Max(5) Byte courierRating,
                @Size(max = 2000) String courierFeedback,
                List<OrderRestaurantRatingSubmission> restaurants) {
}
