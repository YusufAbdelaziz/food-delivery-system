package com.joe.abdelaziz.food_delivery_system.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.joe.abdelaziz.food_delivery_system.region.RegionMapper;

@Mapper(uses = { RegionMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

  AddressDTO toAddressDto(Address address);

  @Mappings(value = {
      @Mapping(target = "customer", ignore = true),
      @Mapping(target = "restaurant", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)
  })
  Address toAddress(AddressDTO addressDto);

  @Mappings(value = {
      @Mapping(target = "customer", ignore = true),
      @Mapping(target = "restaurant", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true)
  })
  Address toUpdatedAddress(AddressDTO dto, @MappingTarget Address existingAddress);
}
