package com.joe.abdelaziz.food_delivery_system.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
import java.util.List;
import com.joe.abdelaziz.food_delivery_system.customer.Customer;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  @Transactional
  @Modifying
  @Query(value = "UPDATE address SET active = false WHERE user_id = :userId AND address_id != :activeAddressId", nativeQuery = true)
  int updateAddressActivityByUserId(Long activeAddressId, Long userId);

  List<Address> findByCustomer(Customer customer);
}
