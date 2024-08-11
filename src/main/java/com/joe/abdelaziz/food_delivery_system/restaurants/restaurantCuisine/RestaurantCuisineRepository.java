package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantCuisineRepository extends JpaRepository<RestaurantCuisine, RestaurantCuisineId> {

}
