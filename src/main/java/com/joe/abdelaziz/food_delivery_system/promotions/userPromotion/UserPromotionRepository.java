package com.joe.abdelaziz.food_delivery_system.promotions.userPromotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPromotionRepository extends JpaRepository<UserPromotion, Long> {

}
