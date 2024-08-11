package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import org.mapstruct.Mapper;

@Mapper
public abstract class RestaurantCuisineIdMapper {

  public abstract RestaurantCuisineIdDTO toRestaurantCuisineIdDTO(RestaurantCuisineId restaurantCuisineId);

  public abstract RestaurantCuisineId toRestaurantCuisineId(RestaurantCuisineIdDTO dto);

}
