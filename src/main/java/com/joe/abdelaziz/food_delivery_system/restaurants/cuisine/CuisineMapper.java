package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisine;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisineDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisineId;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine.RestaurantCuisineIdDTO;

@Mapper()
public abstract class CuisineMapper {

  public abstract CuisineDTO toCuisineDTO(Cuisine restaurantCuisine);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "restaurants", ignore = true)
  })
  public abstract Cuisine toCuisine(CuisineDTO dto);

  @Named("toCuisineWithoutRestaurants")
  @Mapping(target = "restaurants", ignore = true)
  public abstract CuisineDTO toCuisineDTOWithoutRestaurants(Cuisine entity);

  @AfterMapping
  public void linkRestaurantDTOs(Cuisine entity, @MappingTarget CuisineDTO dto) {
    if (entity.getRestaurants() != null) {
      dto.setRestaurants(entity.getRestaurants().stream()
          .map(rc -> {
            RestaurantCuisineDTO restaurantCuisine = new RestaurantCuisineDTO();
            RestaurantCuisineIdDTO id = new RestaurantCuisineIdDTO();
            id.setCuisineId(entity.getId());
            id.setRestaurantId(rc.getRestaurant().getId());
            restaurantCuisine.setId(id);
            restaurantCuisine.setCuisine(dto);
            RestaurantDTO restaurant = new RestaurantDTO();
            restaurant.setId(rc.getRestaurant().getId());
            restaurantCuisine.setRestaurant(restaurant);
            return restaurantCuisine;
          })
          .collect(Collectors.toSet()));
    }

  }

  @AfterMapping
  public void linkRestaurants(@MappingTarget Cuisine entity, CuisineDTO dto) {
    if (dto.getRestaurants() != null) {
      entity.setRestaurants(dto.getRestaurants().stream()
          .map(rc -> {
            RestaurantCuisine restaurantCuisine = new RestaurantCuisine();
            RestaurantCuisineId id = new RestaurantCuisineId();
            id.setCuisineId(entity.getId());
            id.setRestaurantId(rc.getRestaurant().getId());
            restaurantCuisine.setId(id);
            restaurantCuisine.setCuisine(entity);
            Restaurant restaurant = new Restaurant();
            restaurant.setId(rc.getRestaurant().getId());
            restaurantCuisine.setRestaurant(restaurant);
            return restaurantCuisine;
          })
          .collect(Collectors.toSet()));
    }

  }
}
