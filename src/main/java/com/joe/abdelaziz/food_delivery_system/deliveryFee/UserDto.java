// package com.joe.abdelaziz.food_delivery_system.deliveryFee;

// import java.util.HashSet;
// import java.util.Set;

// import com.fasterxml.jackson.annotation.JsonProperty;
// import com.joe.abdelaziz.food_delivery_system.address.AddressDto;
// import com.joe.abdelaziz.food_delivery_system.role.RoleDto;
// import
// com.joe.abdelaziz.food_delivery_system.utiles.validation.NullableEmail;

// import jakarta.persistence.Column;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
// import lombok.Data;

// @Data
// public class UserDto {
// private Long id;

// @NotBlank
// @Size(min = 11)
// @JsonProperty(value = "phone_number")
// private String phoneNumber;

// @NotBlank
// @Size(min = 8, message = "The size of password should be at least of 8
// characters")
// private String password;

// @NotBlank
// @Size(min = 5, message = "The size of name should be at least of 5
// characters")
// private String name;

// @NullableEmail
// @Column(unique = true)
// private String email;

// private Set<AddressDto> addresses = new HashSet<>();

// @JsonProperty(value = "image_url")
// private String imageUrl;

// private RoleDto role;

// private AddressDto activeAddress;
// }
