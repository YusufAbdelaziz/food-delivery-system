package com.joe.abdelaziz.food_delivery_system.orders.deliveryFee;
// package com.joe.abdelaziz.food_delivery_system.deliveryFee;

// import java.util.Set;
// import java.util.stream.Collectors;

// import org.mapstruct.AfterMapping;
// import org.mapstruct.Mapper;
// import org.mapstruct.Mapping;
// import org.mapstruct.MappingTarget;
// import org.mapstruct.Mappings;
// import org.mapstruct.factory.Mappers;

// import com.joe.abdelaziz.food_delivery_system.address.Address;
// import com.joe.abdelaziz.food_delivery_system.address.AddressDto;
// import com.joe.abdelaziz.food_delivery_system.address.AddressMapper;

// @Mapper()
// public interface UserMapper {

// @Mapping(target = "addresses", ignore = true)
// @Mapping(target = "activeAddress", ignore = true)

// UserDto toUserDto(AppUser user);

// @Mappings(value = {
// @Mapping(target = "activeAddress", ignore = true),
// @Mapping(target = "addresses", ignore = true),
// @Mapping(target = "createdBy", ignore = true),
// @Mapping(target = "createdDate", ignore = true),
// @Mapping(target = "lastModifiedBy", ignore = true),
// @Mapping(target = "lastModifiedDate", ignore = true),
// @Mapping(target = "role", ignore = true)

// })
// AppUser toUser(UserDto userDto);

// @AfterMapping
// default void afterMappingUserToDto(AppUser user, @MappingTarget UserDto
// userDto) {
// if (user.getAddresses() != null) {
// AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
// Set<AddressDto> addressDtos = user.getAddresses().stream()
// .map(addressMapper::toAddressDto)
// .collect(Collectors.toSet());
// userDto.setAddresses(addressDtos);
// for (AddressDto addressDto : addressDtos) {
// if (addressDto.isActive()) {
// userDto.setActiveAddress(addressDto);
// break;
// }
// }
// }
// }

// @AfterMapping
// default void afterMappingDtoToUser(UserDto userDto, @MappingTarget AppUser
// user) {
// if (userDto.getAddresses() != null) {
// AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
// Set<Address> addresses = userDto.getAddresses().stream()
// .map(addressMapper::toAddress)
// .collect(Collectors.toSet());
// user.setAddresses(addresses);
// }
// }
// }
