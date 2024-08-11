package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.Cuisine;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineService;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RestaurantCuisineService {

  private final RestaurantCuisineRepository restaurantCuisineRepository;
  private final CuisineService cuisineService;
  private final RestaurantService restaurantService;
  private final RestaurantCuisineMapper restaurantCuisineMapper;
  private final CuisineMapper cuisineMapper;
  private final RestaurantCuisineIdMapper restaurantCuisineIdMapper;

  public RestaurantCuisineDTO getById(RestaurantCuisineIdDTO id) {
    return restaurantCuisineMapper.toRestaurantCuisineDTO(
        restaurantCuisineRepository.findById(restaurantCuisineIdMapper.toRestaurantCuisineId(id))
            .orElseThrow(() -> new RecordNotFoundException(String.format("Cuisine id %d is not found", id))));
  }

  public List<RestaurantCuisineDTO> getAll() {
    return restaurantCuisineRepository.findAll().stream().map(rs -> restaurantCuisineMapper.toRestaurantCuisineDTO(rs))
        .toList();
  }

  public RestaurantCuisineDTO insert(RestaurantCuisineDTO dto) {

    RestaurantCuisine restaurantCuisine = restaurantCuisineMapper.toRestaurantCuisine(dto);
    Restaurant restaurant = restaurantService.getById(restaurantCuisine.getRestaurant().getId());
    Cuisine cuisine = cuisineMapper.toCuisine(cuisineService.getById(restaurantCuisine.getCuisine().getId()));
    RestaurantCuisineId id = new RestaurantCuisineId(restaurant.getId(), cuisine.getId());

    restaurantCuisine.setCuisine(cuisine);
    restaurantCuisine.setRestaurant(restaurant);
    restaurantCuisine.setId(id);

    return restaurantCuisineMapper.toRestaurantCuisineDTO(restaurantCuisineRepository.save(restaurantCuisine));
  }

  public void deleteRestaurantCuisine(RestaurantCuisineIdDTO dto) {
    RestaurantCuisineId id = restaurantCuisineIdMapper.toRestaurantCuisineId(dto);
    restaurantCuisineRepository.deleteById(id);
  }

}
