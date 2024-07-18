package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.region.RegionService;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.EntityNotProvidedException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final RegionService regionService;
  private final CuisineService cuisineService;

  @Transactional
  public Restaurant insertRestaurant(Restaurant restaurant) {
    Region region = restaurant.getAddress().getRegion();
    if (region.getId() != null) {
      Region existingRegion = regionService.findRegionById(region.getId());
      restaurant.getAddress().setRegion(existingRegion);
    } else if (region.getName() != null) {
      Region existingRegion = regionService.insertRegion(region);
      restaurant.getAddress().setRegion(existingRegion);
    } else {
      throw new EntityNotProvidedException("Region object is not provided or neither id nor name is provided");
    }

    return restaurantRepository.save(restaurant);
  }

  public Restaurant getById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a restaurant: id is null");
    }
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Admin of record id %d is not found", id)));
    return restaurant;
  }

  public List<Restaurant> getAll() {
    return restaurantRepository.findAll();
  }

  public List<Restaurant> getRestaurantsByCuisineId(Long cuisineId) {
    // Check if the cuisine even exists
    cuisineService.getById(cuisineId);

    return restaurantRepository.findRestaurantsByCuisineId(cuisineId);
  }
}
