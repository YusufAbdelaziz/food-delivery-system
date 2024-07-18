package com.joe.abdelaziz.food_delivery_system.address;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.customer.CustomerService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;

  private final CustomerService customerService;

  public Address findAddressById(Long id) {
    return addressRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Address id %d is not found", id)));
  }

  public void setAllAddressesActivityToFalse(Long activeAddressId, Long userId) {
    addressRepository.updateAddressActivityByUserId(activeAddressId, userId);
  }

  public Address insertAddress(Address address) {

    Address insertedAddress = addressRepository.save(address);
    if (insertedAddress.isActive()) {
      customerService.updateActiveAddress(insertedAddress, insertedAddress.getCustomer().getId());
      setAllAddressesActivityToFalse(insertedAddress.getId(), insertedAddress.getCustomer().getId());
    }
    return insertedAddress;
  }

}