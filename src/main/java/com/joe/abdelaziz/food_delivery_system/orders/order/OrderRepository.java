package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.Tuple;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query(value = "SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.courierRating IS NULL AND o.status = 'SUCCESSFUL'")
  List<OrderRatingInfo> findUnratedOrdersByCustomerId(Long customerId);

  @Query(value = "SELECT o FROM Order o WHERE o.courier.id = :courierId AND o.status != 'SUCCESSFUL'")
  List<Order> findNotFinishedOrdersByCourierId(Long courierId);

  @Query("SELECT o.courier.id AS courierId, " +
      "o.discountedTotalDeliveryFees AS discountedTotalDeliveryFees, " +
      "o.totalDeliveryFees AS totalDeliveryFees, " +
      "o.courierRating AS courierRating " +
      "FROM Order o " +
      "WHERE o.status = 'SUCCESSFUL' AND o.courierRating IS NOT NULL AND o.courier.id IS NOT NULL")
  List<Tuple> findCouriersStats();

  @Query(value = "SELECT o.courier.id AS courierId, COUNT(o.id) AS totalSuccessfulOrders " +
      "FROM Order o " +
      "WHERE o.status = 'SUCCESSFUL' AND o.courier.id IS NOT NULL " +
      "GROUP BY o.courier.id " +
      "HAVING COUNT(o.id) > 0", nativeQuery = false)
  List<Tuple> findCouriersNumOfSuccessfulOrders();

}
