package com.joe.abdelaziz.food_delivery_system.customer;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.region.RegionService;
import com.joe.abdelaziz.food_delivery_system.security.role.Role;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleService;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleType;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  // TODO : Use the mapper to map to CustomerDTO.

  private final CustomerMapper customerMapper;

  private final RoleService roleService;
  private final RegionService regionService;

  /**
   * Inserts a new user into the system. Note that the user will probably add a
   * single address after registration.
   *
   * @param user the user object to be inserted
   * @return the inserted user object
   */
  @Transactional
  public Customer insertUser(Customer customer) {
    Role role = roleService.findRoleByType(RoleType.USER);
    customer.setRole(role);

    if (customer.getAddresses() != null && !customer.getAddresses().isEmpty()) {
      for (Address address : customer.getAddresses()) {
        customer.addAddress(address); // This will set the customer for the address
        if (address.getRegion() != null) {
          Long id = address.getRegion().getId();
          Region regionById = regionService.findRegionById(id);
          address.setRegion(regionById);
        }

        if (address.getActive()) {
          customer.setActiveAddress(address);
        }
      }
    }

    if (customer.getAddresses().size() == 1) {
      customer.setActiveAddress(customer.getAddresses().iterator().next());

    }

    Customer insertedUser = customerRepository.save(customer);
    return insertedUser;
  }

  public Customer findById(Long id) {
    Customer user = customerRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", id)));
    return user;
  }

  public List<Customer> findAll() {
    return customerRepository.findAllByRoleType(RoleType.USER);
  }

  public void updateActiveAddress(Address activeAddress, Long customerId) {
    Customer customer = findById(customerId);

    customer.setActiveAddress(activeAddress);

    for (Address customerAddress : customer.getAddresses()) {
      if (customerAddress.getId() != activeAddress.getId()) {
        customerAddress.setActive(false);
      }
    }
    activeAddress.setCustomer(customer);
    customerRepository.save(customer);
  }

  public void addAddressToCustomer(Address newAddress, Long customerId) {
    Customer user = findById(customerId);
    user.addAddress(newAddress);
    newAddress.setCustomer(user);
    customerRepository.save(user);
  }

  public Customer updateCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  public Optional<Customer> findByPhoneNumber(String phoneNumber) {
    return customerRepository.findByPhoneNumber(phoneNumber);
  }
}
