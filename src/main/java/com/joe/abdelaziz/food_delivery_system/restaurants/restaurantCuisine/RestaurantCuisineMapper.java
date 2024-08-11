package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.Cuisine;
import com.joe.abdelaziz.food_delivery_system.restaurants.cuisine.CuisineMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;

@Mapper(uses = { RestaurantCuisineIdMapper.class, RestaurantMapper.class, CuisineMapper.class })
public abstract class RestaurantCuisineMapper {
  @Mapping(target = "restaurant", qualifiedByName = "toRestaurantWithoutCuisines")
  @Mapping(target = "cuisine", qualifiedByName = "toCuisineWithoutRestaurants")
  public abstract RestaurantCuisineDTO toRestaurantCuisineDTO(RestaurantCuisine restaurantCuisine);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
  })
  public abstract RestaurantCuisine toRestaurantCuisine(RestaurantCuisineDTO dto);

  @AfterMapping
  public void linkRestaurantAndCuisine(@MappingTarget RestaurantCuisine entity, RestaurantCuisineDTO dto) {
    if (dto.getRestaurant() != null) {
      Restaurant restaurant = new Restaurant();
      restaurant.setId(dto.getRestaurant().getId());
      entity.setRestaurant(restaurant);
    }
    if (dto.getCuisine() != null) {
      Cuisine cuisine = new Cuisine();
      cuisine.setId(dto.getCuisine().getId());
      entity.setCuisine(cuisine);
    }
  }
}
