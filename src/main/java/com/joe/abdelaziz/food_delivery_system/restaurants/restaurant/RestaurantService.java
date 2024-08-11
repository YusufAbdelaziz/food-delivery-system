package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantService;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantStatsDTO;
import com.joe.abdelaziz.food_delivery_system.region.RegionDTO;
import com.joe.abdelaziz.food_delivery_system.region.RegionService;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

  private final RestaurantRepository restaurantRepository;
  private final RegionService regionService;
  private final CuisineService cuisineService;
  private final RestaurantMapper restaurantMapper;
  private final OrderRestaurantService orderRestaurantService;

  @Transactional
  public RestaurantDTO insertRestaurant(RestaurantDTO dto) {
    RegionDTO region = dto.getAddress().getRegion();
    if (region.getId() != null) {
      RegionDTO existingRegion = regionService.findRegionDTOById(region.getId());
      dto.getAddress().setRegion(existingRegion);
    } else if (region.getName() != null) {
      RegionDTO existingRegion = regionService.insertRegion(region);
      dto.getAddress().setRegion(existingRegion);
    } else {
      throw new BusinessLogicException("Region object is not provided or neither id nor name is provided");
    }

    return restaurantMapper.toRestaurantDTO(restaurantRepository.save(restaurantMapper.toRestaurant(dto)));
  }

  public Restaurant getById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a restaurant: id is null");
    }
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Restaurant of record id %d is not found", id)));
    return restaurant;
  }

  public RestaurantDTO getDTOById(Long id) {
    return restaurantMapper.toRestaurantDTO(getById(id));
  }

  public List<RestaurantDTO> getAll() {
    return restaurantRepository.findAll().stream()
        .map(restaurant -> restaurantMapper.toRestaurantDTO(restaurant))
        .toList();
  }

  public List<RestaurantDTO> getRestaurantsByCuisineId(Long cuisineId) {
    // Check if the cuisine even exists
    cuisineService.getById(cuisineId);

    return restaurantRepository.findRestaurantsByCuisineId(cuisineId).stream()
        .map(restaurant -> restaurantMapper.toRestaurantDTO(restaurant))
        .toList();
  }

  public void updateVisibility(Long restaurantId, boolean visible) {
    restaurantRepository.updateVisibility(restaurantId, visible);
  }

  public void deleteRestaurant(Long restaurantId) {
    restaurantRepository.deleteById(restaurantId);
  }

  public RestaurantDTO updateRestaurant(RestaurantDTO dto) {
    Restaurant existingRestaurant = getById(dto.getId());

    Restaurant updatedRestaurant = restaurantMapper.toUpdatedRestaurant(dto, existingRestaurant);
    return restaurantMapper.toRestaurantDTO(restaurantRepository.save(updatedRestaurant));

  }

  public List<RestaurantDTO> getByNameContaining(String restaurantName) {
    return restaurantRepository.findByNameContainingIgnoreCase(restaurantName).stream()
        .map(rest -> restaurantMapper.toRestaurantDTO(rest)).toList();
  }

  public void incrementSuccessfulOrdersCount(List<Restaurant> restaurants) {
    for (Restaurant restaurant : restaurants) {
      restaurant.setSuccessfulOrders(restaurant.getSuccessfulOrders() + 1);
    }
    restaurantRepository.saveAll(restaurants);
  }

  @Transactional
  public void updateAvgRatingsForAllRestaurants() {
    List<OrderRestaurantStatsDTO> orderRestaurantStats = orderRestaurantService.findRestaurantsAvgRating();

    for (OrderRestaurantStatsDTO orderRestaurantStatsDTO : orderRestaurantStats) {
      restaurantRepository.updateAvgRating(orderRestaurantStatsDTO.getAvgRating(),
          orderRestaurantStatsDTO.getRestaurantId());
    }

  }
}
