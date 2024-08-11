package com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecRepository extends JpaRepository<Spec, Long> {

}
