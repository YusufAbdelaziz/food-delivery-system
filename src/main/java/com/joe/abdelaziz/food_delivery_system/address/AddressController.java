package com.joe.abdelaziz.food_delivery_system.address;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/addresses")
@AllArgsConstructor
@Validated
public class AddressController {

  private final AddressService addressService;

  @PostMapping("")
  public ResponseEntity<Address> addUser(@Valid @RequestBody Address address) {
    Address insertedAddress = addressService.insertAddress(address);

    return ResponseEntity.ok().body(insertedAddress);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Address> getUserById(@PathVariable Long id) {
    return ResponseEntity.ok().body(addressService.findAddressById(id));
  }

}
