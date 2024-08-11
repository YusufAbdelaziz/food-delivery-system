package com.joe.abdelaziz.food_delivery_system.region;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper()
public interface RegionMapper {

  RegionDTO toRegionDTO(Region region);

  @Mappings(value = {
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "addresses", ignore = true)
  })
  Region toRegion(RegionDTO dto);
}
