package com.joe.abdelaziz.food_delivery_system.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.joe.abdelaziz.food_delivery_system.role.RoleType;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("SELECT a FROM Customer a WHERE a.role.type = :roleType")
  List<Customer> findAllByRoleType(RoleType roleType);
}
