// package com.joe.abdelaziz.food_delivery_system.address;

// import org.mapstruct.AfterMapping;
// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
// import org.mapstruct.MappingTarget;
// import org.mapstruct.Mappings;
// import org.mapstruct.factory.Mappers;

// import com.joe.abdelaziz.food_delivery_system.base.AppUser;
// import com.joe.abdelaziz.food_delivery_system.user.UserDto;
// import com.joe.abdelaziz.food_delivery_system.user.UserMapper;

// @Mapper()
// public interface AddressMapper {

// @Mapping(target = "userId.addresses", ignore = true)
// AddressDto toAddressDto(Address address);

// @Mappings(value = {
// @Mapping(target = "user.addresses", ignore = true),
// @Mapping(target = "createdBy", ignore = true),
// @Mapping(target = "createdDate", ignore = true),
// @Mapping(target = "lastModifiedBy", ignore = true),
// @Mapping(target = "lastModifiedDate", ignore = true)
// })
// Address toAddress(AddressDto addressDto);

// @AfterMapping
// default void afterMappingAddressToDto(Address address, @MappingTarget
// AddressDto addressDto) {
// if (address.getUser() != null) {
// UserMapper mapper = Mappers.getMapper(UserMapper.class);
// UserDto userDto = mapper.toUserDto(address.getUser());
// addressDto.setUserId(userDto);
// }
// }

// @AfterMapping
// default void afterMappingDtoToAddress(AddressDto addressDto, @MappingTarget
// Address address) {
// if (addressDto.getUserId() != null) {
// UserMapper mapper = Mappers.getMapper(UserMapper.class);

// AppUser user = mapper.toUser(addressDto.getUserId());
// address.setUser(user);
// }
// }
// }
