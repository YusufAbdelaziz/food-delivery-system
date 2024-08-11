package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  @Query(value = "SELECT r FROM Restaurant r INNER JOIN RestaurantCuisine rc ON r.id = rc.restaurant.id AND rc.cuisine.id = :cuisineId")
  List<Restaurant> findRestaurantsByCuisineId(Long cuisineId);

  @Modifying
  @Transactional
  @Query("UPDATE Restaurant r SET r.visible = :value WHERE r.id = :id")
  int updateVisibility(Long id, boolean value);

  @Query("SELECT r FROM Restaurant r WHERE (r.name) LIKE (CONCAT('%', :name, '%'))")
  List<Restaurant> findByNameContainingIgnoreCase(@Param("name") String name);

  @Modifying
  @Transactional
  @Query("UPDATE Restaurant r SET r.avgRating = :avgRating WHERE r.id = :restaurantId")
  int updateAvgRating(BigDecimal avgRating, Long restaurantId);
}
