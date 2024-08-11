package com.joe.abdelaziz.food_delivery_system.promotions.promotion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
  Optional<Promotion> findByCodeAndActiveTrue(String code);
}
