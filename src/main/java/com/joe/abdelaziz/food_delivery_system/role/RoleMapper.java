// package com.joe.abdelaziz.food_delivery_system.role;

// import java.util.SortedSet;

// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
// import org.mapstruct.Mappings;

// @Mapper(imports = SortedSet.class)
// public interface RoleMapper {

// RoleDto map(Role role);

// @Mappings(value = {
// @Mapping(target = "createdBy", ignore = true),
// @Mapping(target = "createdDate", ignore = true),
// @Mapping(target = "lastModifiedBy", ignore = true),
// @Mapping(target = "lastModifiedDate", ignore = true)
// })
// Role unMap(RoleDto roleDto);
// }
