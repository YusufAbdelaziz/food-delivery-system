// package com.joe.abdelaziz.food_delivery_system.utiles.runner;

// import java.util.HashSet;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.joe.abdelaziz.food_delivery_system.address.Address;
// import com.joe.abdelaziz.food_delivery_system.address.AddressMapper;
// import com.joe.abdelaziz.food_delivery_system.address.AddressService;
// import com.joe.abdelaziz.food_delivery_system.region.Region;
// import com.joe.abdelaziz.food_delivery_system.region.RegionDto;
// import com.joe.abdelaziz.food_delivery_system.region.RegionMapper;
// import com.joe.abdelaziz.food_delivery_system.region.RegionService;
// import com.joe.abdelaziz.food_delivery_system.role.Role;
// import com.joe.abdelaziz.food_delivery_system.role.RoleService;
// import com.joe.abdelaziz.food_delivery_system.role.RoleType;
// import com.joe.abdelaziz.food_delivery_system.user.AppUser;
// import com.joe.abdelaziz.food_delivery_system.user.UserDto;
// import com.joe.abdelaziz.food_delivery_system.user.UserMapper;
// import com.joe.abdelaziz.food_delivery_system.user.UserService;

// import lombok.AllArgsConstructor;

// @Component
// @AllArgsConstructor
// public class DeliverySysCommandRunner implements CommandLineRunner {

// private final RegionService regionService;
// private final RoleService roleService;

// private final UserService userService;

// private final AddressService addressService;

// private final RegionMapper regionMapper;

// private final UserMapper userMapper;

// private final AddressMapper addressMapper;

// @Override
// public void run(String... args) throws Exception {
// if (userService.findAll().isEmpty()) {

// Role adminRole = new Role();
// adminRole.setType(RoleType.ADMIN);
// adminRole = roleService.insertRole(adminRole);

// Role userRole = new Role();
// userRole.setType(RoleType.USER);
// userRole = roleService.insertRole(userRole);

// // Create regions
// Region region1 = new Region();
// region1.setName("Central Region");
// RegionDto region1Dto =
// regionService.insertRegion(regionMapper.toRegionDto(region1));

// Region region2 = new Region();
// region2.setName("West Region");
// RegionDto region2Dto =
// regionService.insertRegion(regionMapper.toRegionDto(region2));

// // Create users
// AppUser user1 = new AppUser();
// user1.setPhoneNumber("123456789012");
// user1.setPassword("password123");
// user1.setName("John Doe");
// user1.setEmail("john.doe@example.com");
// user1.setRole(userRole);
// user1.setAddresses(new HashSet<>());
// UserDto user1Dto = userService.insertUser(user1);

// AppUser user2 = new AppUser();
// user2.setPhoneNumber("123456789013");
// user2.setPassword("password1234");
// user2.setName("Jane Doe");
// user2.setEmail("jane.doe@example.com");
// user2.setRole(userRole);
// user2.setAddresses(new HashSet<>());
// UserDto user2Dto = userService.insertUser(user2);

// // Create addresses
// Address address1 = new Address();
// address1.setLatitude(40.7128);
// address1.setLongitude(-74.0060);
// address1.setDescription("John's Home Address");
// address1.setUser(user1);
// address1.setRegion(regionMapper.toRegion(region1Dto));
// address1.setBuildingNumber((short) 123);
// address1.setApartmentNumber((short) 45);
// address1 = addressService.insertAddress(address1);

// Address address2 = new Address();
// address2.setLatitude(34.0522);
// address2.setLongitude(-118.2437);
// address2.setDescription("Jane's Home Address");
// address2.setUser(user2);
// address2.setRegion(regionMapper.toRegion(region2Dto));
// address2.setBuildingNumber((short) 678);
// address2.setApartmentNumber((short) 90);
// address2 = addressService.insertAddress(address2);

// // Add addresses to users
// user1Dto.getAddresses().add(addressMapper.toAddressDto(address1));
// user2Dto.getAddresses().add(addressMapper.toAddressDto(address2));
// userService.insertUser(userMapper.toUser(user1Dto));
// userService.insertUser(userMapper.toUser(user2Dto));
// }

// }

// }
