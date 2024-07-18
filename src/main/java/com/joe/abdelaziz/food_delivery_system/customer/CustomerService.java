package com.joe.abdelaziz.food_delivery_system.customer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.region.RegionService;
import com.joe.abdelaziz.food_delivery_system.role.Role;
import com.joe.abdelaziz.food_delivery_system.role.RoleService;
import com.joe.abdelaziz.food_delivery_system.role.RoleType;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  private final RoleService roleService;
  private final RegionService regionService;

  /**
   * Inserts a new user into the system. Note that the user will probably add a
   * single address after registration.
   *
   * @param user the user object to be inserted
   * @return the inserted user object
   */
  @Transactional()
  public Customer insertUser(Customer customer) {
    Role role = roleService.findRoleByType(RoleType.USER);
    customer.setRole(role);

    // Based on requirements, it's guaranteed that the set of address will contain
    // only one address
    if (customer.getAddresses() != null) {
      for (Address address : customer.getAddresses()) {
        address.setCustomer(customer);
        if (address.getRegion() != null) {
          Long id = address.getRegion().getId();
          Region regionById = regionService.findRegionById(id);
          address.setRegion(regionById);
        }

        if (address.isActive()) {
          customer.setActiveAddress(address);
        }
      }
    }

    Customer insertedUser = customerRepository.save(customer);
    return insertedUser;
  }

  public Customer findCustomerById(Long id) {
    Customer user = customerRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", id)));
    return user;
  }

  public List<Customer> findAll() {
    return customerRepository.findAllByRoleType(RoleType.USER);
  }

  public void updateActiveAddress(Address activeAddress, Long customerId) {
    Customer user = customerRepository.findById(customerId)
        .orElseThrow(() -> new RecordNotFoundException(String.format("User of record id %d is not found", customerId)));

    user.setActiveAddress(activeAddress);

    for (Address userAddress : user.getAddresses()) {
      if (userAddress.getId() != activeAddress.getId()) {
        userAddress.setActive(false);
      }
    }

    customerRepository.save(user);
  }

}
