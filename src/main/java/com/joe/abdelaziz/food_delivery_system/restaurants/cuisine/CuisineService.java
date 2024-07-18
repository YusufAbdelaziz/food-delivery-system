package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CuisineService {

  private final CuisineRepository cuisineRepository;

  public Cuisine getById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a cuisine: id is null");
    }
    return cuisineRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Cuisine id %d is not found", id)));
  }

  public Cuisine insert(Cuisine cuisine) {
    return cuisineRepository.save(cuisine);
  }
}
