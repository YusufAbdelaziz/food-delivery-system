package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.Cuisine;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineService;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantService;
import com.joe.abdelaziz.food_delivery_system.utiles.compositeKeys.RestaurantCuisineId;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantCuisineService {

  private final RestaurantCuisineRepository restaurantCuisineRepository;
  private final CuisineService cuisineService;
  private final RestaurantService restaurantService;

  public RestaurantCuisine getById(RestaurantCuisineId id) {
    return restaurantCuisineRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Cuisine id %d is not found", id)));
  }

  public List<RestaurantCuisine> getAll() {
    return restaurantCuisineRepository.findAll();
  }

  public RestaurantCuisine insert(RestaurantCuisine restaurantCuisine) {

    Restaurant restaurant = restaurantService.getById(restaurantCuisine.getRestaurant().getId());
    Cuisine cuisine = cuisineService.getById(restaurantCuisine.getCuisine().getId());
    RestaurantCuisineId id = new RestaurantCuisineId(restaurant.getId(), cuisine.getId());

    restaurantCuisine.setCuisine(cuisine);
    restaurantCuisine.setRestaurant(restaurant);
    restaurantCuisine.setId(id);

    return restaurantCuisineRepository.save(restaurantCuisine);
  }

}
