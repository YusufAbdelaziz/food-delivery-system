package com.joe.abdelaziz.food_delivery_system.region;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegionService {

  private final RegionRepository regionRepository;

  // private RegionMapper regionMapper;

  public Region findRegionById(Long id) {
    Optional<Region> opRegion = regionRepository.findById(id);
    if (opRegion.isEmpty()) {
      throw new EntityNotFoundException(String.format("Region id %d is not found", id));
    }
    return opRegion.get();
  }

  public Region insertRegion(Region region) {
    Region insertedRegion = regionRepository.save(region);
    return insertedRegion;
  }

  public List<Region> findAll() {
    return regionRepository.findAll();
  }

}