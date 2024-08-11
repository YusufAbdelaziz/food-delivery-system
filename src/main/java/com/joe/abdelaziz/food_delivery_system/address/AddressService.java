package com.joe.abdelaziz.food_delivery_system.address;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

  private final AddressRepository addressRepository;
  private final AddressMapper addressMapper;
  private final CustomerService customerService;

  public AddressDTO findDTOById(Long id) {
    return addressMapper.toAddressDto(findById(id));
  }

  private Address findById(Long id) {
    return addressRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Address id %d is not found", id)));
  }

  public List<AddressDTO> findAddressesByCustomerId(Long id) {
    Customer customer = customerService.findById(id);
    return addressRepository.findByCustomer(customer).stream().map(address -> addressMapper.toAddressDto(address))
        .toList();
  }

  public void setAllAddressesActivityToFalse(Long activeAddressId, Long userId) {
    addressRepository.updateAddressActivityByUserId(activeAddressId, userId);
  }

  public AddressDTO insertAddress(AddressDTO dto, Long userId) {

    Address address = addressMapper.toAddress(dto);

    if (address.getActive()) {
      customerService.updateActiveAddress(address, userId);
      setAllAddressesActivityToFalse(address.getId(), userId);
    } else {
      customerService.addAddressToCustomer(address, userId);
    }
    Address insertedAddress = addressRepository.save(address);
    return addressMapper.toAddressDto(insertedAddress);
  }

  public AddressDTO setAddressToActive(Long addressId) {
    Address address = findById(addressId);
    address.setActive(true);
    setAllAddressesActivityToFalse(addressId, address.getCustomer().getId());
    return addressMapper.toAddressDto(addressRepository.save(address));
  }

  public AddressDTO updateAddress(AddressDTO updatedAddressDto) {
    Address existingAddress = findById(updatedAddressDto.getId());
    Address updatedAddress = addressMapper.toUpdatedAddress(updatedAddressDto, existingAddress);
    return addressMapper.toAddressDto(addressRepository.save(updatedAddress));
  }

  @Transactional
  public void deleteAddress(Long addressId) {
    Address address = findById(addressId);
    Customer customer = address.getCustomer();
    customer.removeAddress(address);
    customerService.updateCustomer(customer);
    addressRepository.delete(address);
  }
}