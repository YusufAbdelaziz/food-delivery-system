package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CuisineService {

  private final CuisineRepository cuisineRepository;
  private final CuisineMapper cuisineMapper;

  public CuisineDTO getById(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a cuisine: id is null");
    }
    return cuisineMapper.toCuisineDTOWithoutRestaurants(cuisineRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Cuisine id %d is not found", id))));
  }

  public CuisineDTO insert(CuisineDTO cuisine) {
    Cuisine newCuisine = cuisineRepository.save(cuisineMapper.toCuisine(cuisine));
    return cuisineMapper.toCuisineDTOWithoutRestaurants(newCuisine);
  }

  public List<CuisineDTO> getAll() {
    return cuisineRepository.findAll().stream().map(cuisine -> cuisineMapper.toCuisineDTOWithoutRestaurants(cuisine))
        .toList();
  }
}
