package com.joe.abdelaziz.food_delivery_system.courier;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.orders.order.OrderDTO;
import com.joe.abdelaziz.food_delivery_system.orders.order.OrderService;
import com.joe.abdelaziz.food_delivery_system.security.role.Role;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleService;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleType;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourierService {

  private final CourierRepository courierRepository;
  private final CourierMapper courierMapper;
  private final RoleService roleService;
  private final OrderService orderService;

  public CourierDTO insertCourier(CourierDTO dto) {
    Courier courier = courierMapper.toCourier(dto);
    Role role = roleService.findRoleByType(RoleType.COURIER);
    courier.setRole(role);

    Courier savedCourier = courierRepository.save(courier);
    return courierMapper.toCourierDTO(savedCourier);
  }

  public Courier insertCourier(Courier courier) {
    Role role = roleService.findRoleByType(RoleType.COURIER);
    courier.setRole(role);

    return courierRepository.save(courier);
  }

  public Courier findById(Long id) {
    Courier courier = courierRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Courier id %d is not found", id)));
    return courier;
  }

  public CourierDTO findDtoById(Long id) {
    Courier courier = courierRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Courier id %d is not found", id)));
    return courierMapper.toCourierDTO(courier);
  }

  public List<CourierDTO> findAll() {
    return courierRepository.findAll().stream().map(courier -> courierMapper.toCourierDTO(courier)).toList();
  }

  @Transactional
  public OrderDTO assignOrderToCourier(Long courierId, Long orderId) {
    Order existingOrder = orderService.findById(orderId);
    Courier originalCourier = existingOrder.getCourier();
    if (originalCourier != null && originalCourier.getId() != null) {
      originalCourier.removeOrder(orderId);
      courierRepository.save(originalCourier);
    }
    Courier courier = findById(courierId);
    courier.addOrder(existingOrder);
    courierRepository.save(courier);
    return orderService.changeOrderCourier(courier, existingOrder);

  }

  public CourierDTO updateCourier(CourierDTO dto) {
    Courier courier = findById(dto.getId());
    Courier updatedCourier = courierMapper.toUpdatedCourier(dto, courier);
    return courierMapper.toCourierDTO(courierRepository.save(updatedCourier));
  }

  public Optional<Courier> findByPhoneNumber(String phoneNumber) {
    return courierRepository.findByPhoneNumber(phoneNumber);
  }

  public List<OrderDTO> findOrdersByCourierId(Long courierId) {
    return orderService.findOrdersByCourierId(courierId);
  }

  public void updateCouriersStats() {
    List<Tuple> dataTuples = orderService.findCourierStats();
    List<CourierStatsDTO> stats = calculateCourierStats(dataTuples);
    for (CourierStatsDTO stat : stats) {
      courierRepository.updateCourierRatingAndEarning(stat.getCourierId(), stat.getAvgRating(), stat.getTotalEarning());
    }
  }

  public void updateCouriersTotalOrders() {
    List<Tuple> dataTuples = orderService.findCouriersNumOfSuccessfulOrders();

    for (Tuple tuple : dataTuples) {
      Long courierId = tuple.get("courierId", Long.class);
      Long totalSuccessfulOrders = tuple.get("totalSuccessfulOrders", Long.class);
      courierRepository.updateNumOfSuccessfulOrders(courierId, totalSuccessfulOrders);
    }
  }

  private List<CourierStatsDTO> calculateCourierStats(List<Tuple> dataTuples) {

    Map<Long, CourierStatsDTO> courierStatsMap = new HashMap<>();
    Map<Long, Integer> courierDeliveryCount = new HashMap<>();

    for (Tuple tuple : dataTuples) {
      Long courierId = tuple.get("courierId", Long.class);
      BigDecimal discountedDeliveryFees = tuple.get("discountedTotalDeliveryFees", BigDecimal.class);
      BigDecimal totalDeliveryFees = tuple.get("totalDeliveryFees", BigDecimal.class);
      Byte courierRating = tuple.get("courierRating", Byte.class);

      CourierStatsDTO stats = courierStatsMap.computeIfAbsent(courierId,
          id -> new CourierStatsDTO(id, BigDecimal.ZERO, BigDecimal.ZERO));

      // Sum the delivery fees, using discounted if available, otherwise use total
      BigDecimal feeToAdd = (discountedDeliveryFees != null) ? discountedDeliveryFees : totalDeliveryFees;
      stats.setTotalEarning(stats.getTotalEarning().add(feeToAdd));

      // Add to the rating sum (the average is calculated later)
      stats.setAvgRating(stats.getAvgRating().add(BigDecimal.valueOf(courierRating)));

      // Increment the delivery count for this courier
      courierDeliveryCount.merge(courierId, 1, Integer::sum);
    }

    // Calculate average rating for each courier and round to 2 decimal places
    return courierStatsMap.values().stream()
        .peek(stats -> {
          int deliveryCount = courierDeliveryCount.get(stats.getCourierId());
          BigDecimal avgRating = stats.getAvgRating().divide(BigDecimal.valueOf(deliveryCount), 2,
              RoundingMode.HALF_UP);
          stats.setAvgRating(avgRating);
        })
        .collect(Collectors.toList());
  }


}
