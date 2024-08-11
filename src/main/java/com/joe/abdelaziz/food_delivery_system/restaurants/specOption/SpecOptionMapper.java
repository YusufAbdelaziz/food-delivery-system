package com.joe.abdelaziz.food_delivery_system.restaurants.specOption;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper
public abstract class SpecOptionMapper {

  public abstract SpecOptionDTO toSpecOptionDTO(SpecOption specOption);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true), })
  public abstract SpecOption toSpecOption(SpecOptionDTO dto);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true), })
  public abstract SpecOption toUpdatedSpecOption(SpecOptionDTO dto, @MappingTarget SpecOption existingSpecOption);
}
