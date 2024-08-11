package com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.joe.abdelaziz.food_delivery_system.region.Region;

@Repository
public interface DeliveryFeeRepository extends JpaRepository<DeliveryFee, Long> {

  Optional<DeliveryFee> findByToRegionAndFromRegion(Region toRegion, Region fromRegion);

}
