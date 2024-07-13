// package com.joe.abdelaziz.food_delivery_system.region;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
// import org.mapstruct.Mappings;

// import com.joe.abdelaziz.food_delivery_system.address.AddressMapper;

// @Mapper(uses = { AddressMapper.class })
// public interface RegionMapper {

// @Mapping(target = "addresses", ignore = true)
// RegionDto toRegionDto(Region region);

// @Mappings(value = {
// @Mapping(target = "createdBy", ignore = true),
// @Mapping(target = "createdDate", ignore = true),
// @Mapping(target = "lastModifiedBy", ignore = true),
// @Mapping(target = "lastModifiedDate", ignore = true),
// @Mapping(target = "addresses", ignore = true)

// })
// Region toRegion(RegionDto dto);
// }
