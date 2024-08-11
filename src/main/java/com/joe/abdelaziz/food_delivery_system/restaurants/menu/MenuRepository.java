package com.joe.abdelaziz.food_delivery_system.restaurants.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

  @Query("SELECT m FROM Menu m WHERE m.restaurant.id = :restaurantId")
  Menu findByRestaurant(Long restaurantId);
}
