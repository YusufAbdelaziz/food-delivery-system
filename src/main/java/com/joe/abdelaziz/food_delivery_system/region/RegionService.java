package com.joe.abdelaziz.food_delivery_system.region;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionService {

  private final RegionRepository regionRepository;
  private final RegionMapper regionMapper;

  public Region findRegionById(Long id) {
    Optional<Region> opRegion = regionRepository.findById(id);
    if (opRegion.isEmpty()) {
      throw new EntityNotFoundException(String.format("Region id %d is not found", id));
    }
    return opRegion.get();
  }

  public RegionDTO findRegionDTOById(Long id) {
    return regionMapper.toRegionDTO(findRegionById(id));
  }

  public RegionDTO insertRegion(RegionDTO dto) {
    Region region = regionMapper.toRegion(dto);
    Region insertedRegion = regionRepository.save(region);
    return regionMapper.toRegionDTO(insertedRegion);
  }

  public List<RegionDTO> findAll() {
    return regionRepository.findAll().stream().map(region -> regionMapper.toRegionDTO(region)).toList();
  }

}