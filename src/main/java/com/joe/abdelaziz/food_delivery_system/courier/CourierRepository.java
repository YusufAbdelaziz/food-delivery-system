package com.joe.abdelaziz.food_delivery_system.courier;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

  Optional<Courier> findByPhoneNumber(String phoneNumber);

  @Modifying
  @Transactional
  @Query("UPDATE Courier c SET c.avgRating = :avgRating, c.earnings = :earnings WHERE c.id = :courierId")
  int updateCourierRatingAndEarning(Long courierId, BigDecimal avgRating, BigDecimal earnings);

  @Modifying
  @Transactional
  @Query("UPDATE Courier c SET c.successfulOrders = :numOfSuccessfulOrders WHERE c.id = :courierId")
  int updateNumOfSuccessfulOrders(Long courierId, Long numOfSuccessfulOrders);

}
