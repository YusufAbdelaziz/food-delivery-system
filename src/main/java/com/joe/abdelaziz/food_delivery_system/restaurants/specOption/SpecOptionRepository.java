package com.joe.abdelaziz.food_delivery_system.restaurants.specOption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecOptionRepository extends JpaRepository<SpecOption, Long> {

}
