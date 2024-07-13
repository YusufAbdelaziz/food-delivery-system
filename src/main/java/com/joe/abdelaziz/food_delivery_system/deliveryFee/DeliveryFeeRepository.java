package com.joe.abdelaziz.food_delivery_system.deliveryFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryFeeRepository extends JpaRepository<DeliveryFee, Long> {
}
