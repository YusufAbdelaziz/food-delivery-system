package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  @Query(value = "SELECT r FROM Restaurant r INNER JOIN RestaurantCuisine rc ON r.id = rc.restaurant.id AND rc.cuisine.id = :cuisineId")
  List<Restaurant> findRestaurantsByCuisineId(Long cuisineId);
}
