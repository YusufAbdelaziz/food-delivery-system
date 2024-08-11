package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.courier.Courier;
import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerMapper;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerService;
import com.joe.abdelaziz.food_delivery_system.orders.orderItem.OrderItemDTO;
import com.joe.abdelaziz.food_delivery_system.orders.orderOption.OrderOptionDTO;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurant;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantDTO;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurantRatingSubmission;
import com.joe.abdelaziz.food_delivery_system.orders.orderSpec.OrderSpecDTO;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFee;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFeeDTO;
import com.joe.abdelaziz.food_delivery_system.orders.restaurantDeliveryFee.RestaurantDeliveryFeeService;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.Promotion;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.PromotionService;
import com.joe.abdelaziz.food_delivery_system.promotions.userPromotion.UserPromotionService;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
  private final OrderRepository orderRepository;
  private final RestaurantDeliveryFeeService orderDeliveryFeeService;
  private final CustomerService customerService;
  private final RestaurantService restaurantService;
  private final RestaurantMapper restaurantMapper;
  private final CustomerMapper customerMapper;
  private final PromotionService promotionService;
  private final OrderMapper orderMapper;
  private final UserPromotionService userPromotionService;

  public OrderDTO insertOrder(OrderDTO dto) {
    Order order = orderMapper.toOrder(dto);
    if (dto.getCustomer() == null || dto.getCustomer().getId() == null) {
      throw new BusinessLogicException("A customer should be provided when placing an order");
    }

    if (dto.getRestaurants().isEmpty()) {
      throw new BusinessLogicException("An order must contain at least 1 restaurant");
    }
    Customer customer = customerService.findById(dto.getCustomer().getId());
    order.setCustomer(customer);
    Promotion promotion = null;
    if (dto.getPromotion() != null && dto.getPromotion().getId() != null) {
      promotion = promotionService.getById(dto.getPromotion().getId());
      // You should revalidate the promotion to make sure the promotion is still valid
      // right before the order placement.
      promotionService.validatePromotion(promotion);
      order.setPromotion(promotion);
    }

    // Estimated delivery date should be recalculated because the user may
    // add new address, review the order, etc so that would consume a couple of
    // minutes
    order.setEstimatedDeliveryDate(calculateOverallEstimatedDeliveryTime(order.getRestaurants()));
    order.setStatus(OrderStatus.PENDING);

    for (OrderRestaurant orderRestaurant : order.getRestaurants()) {
      orderRestaurant.setOrder(order);

      RestaurantDeliveryFee deliveryFee = orderRestaurant.getDeliveryFee();
      if (deliveryFee != null) {
        deliveryFee.setOrderRestaurant(orderRestaurant);
      }
    }

    Order newOrder = orderRepository.save(order);

    if (promotion != null) {
      promotionService.incrementUsageCount(promotion);
      userPromotionService.assignPromotionToUser(promotion, customer);
    }

    return orderMapper.toOrderDTO(newOrder);
  }

  public OrderDTO calculateReceipt(OrderDTO order) {
    Customer customer = customerService.findById(order.getCustomer().getId());
    order.setCustomer(customerMapper.toOrderCustomerDTO(customer));
    for (OrderRestaurantDTO orderRestaurant : order.getRestaurants()) {
      Restaurant restaurant = restaurantService.getById(orderRestaurant.getExistingRestaurant().getId());
      orderRestaurant.setExistingRestaurant(restaurantMapper.toRestaurantDTO(restaurant));
      BigDecimal restaurantOrderTotal = calculateRestaurantReceiptWithoutDeliveryFee(orderRestaurant);
      order.setOrderTotal(order.getOrderTotal().add(restaurantOrderTotal));
      RestaurantDeliveryFeeDTO restaurantDeliveryFee = orderDeliveryFeeService
          .calculateRestaurantDeliveryFee(orderRestaurant, customer);
      orderRestaurant
          .setDeliveryFee(restaurantDeliveryFee);
      BigDecimal deliveryFeePrice = orderRestaurant.getDeliveryFee().getCurrentPrice();
      order.setTotalDeliveryFees(
          order.getTotalDeliveryFees().add(deliveryFeePrice));
    }

    order.setEstimatedDeliveryDate(calculateOverallEstimatedDeliveryTime(order.getRestaurants()));
    return order;
  }

  private <T> LocalDateTime calculateOverallEstimatedDeliveryTime(Set<T> restaurants) {
    // Adds 15 minutes for each additional restaurant.
    int additionalBufferMinutes = 15 * (restaurants.size() - 1);

    Duration maxDuration = restaurants.stream()
        .map(restaurant -> getEstimatedDuration(restaurant))
        .max(Duration::compareTo)
        .orElseThrow(() -> new IllegalArgumentException("No restaurants in the order"));

    return LocalDateTime.now().plusMinutes(additionalBufferMinutes).plusSeconds(maxDuration.getSeconds());
  }

  private Duration getEstimatedDuration(Object restaurant) {
    if (restaurant instanceof OrderRestaurantDTO) {
      return ((OrderRestaurantDTO) restaurant).getDeliveryFee().getEstimatedDuration();
    } else if (restaurant instanceof OrderRestaurant) {
      return ((OrderRestaurant) restaurant).getDeliveryFee().getEstimatedDuration();
    } else {
      throw new IllegalArgumentException("Unsupported restaurant type");
    }
  }

  private BigDecimal calculateRestaurantReceiptWithoutDeliveryFee(OrderRestaurantDTO orderRestaurant) {
    BigDecimal totalRestaurantReceipt = BigDecimal.ZERO;
    for (OrderItemDTO orderItem : orderRestaurant.getItems()) {
      BigDecimal itemPrice = orderItem.getPrice();
      for (OrderSpecDTO spec : orderItem.getSpecs()) {
        for (OrderOptionDTO option : spec.getOptions()) {
          itemPrice = itemPrice.add(option.getPrice());
        }
      }
      itemPrice = itemPrice.multiply(BigDecimal.valueOf(orderItem.getQuantity()));
      totalRestaurantReceipt = totalRestaurantReceipt.add(itemPrice);
    }

    orderRestaurant.setTotalRestaurantReceipt(totalRestaurantReceipt);
    return orderRestaurant.getTotalRestaurantReceipt();
  }

  public OrderDTO applyPromotionCode(OrderDTO order) {
    if (order.getPromotion() == null || order.getPromotion().getCode() == null
        || order.getPromotion().getCode().isEmpty()) {
      throw new BusinessLogicException("You should provide a valid promotion code");
    }
    return promotionService.applyPromotionCode(order.getPromotion().getCode(), order);
  }

  public OrderDTO findDTOById(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Order id %d is not found", id)));
    return orderMapper.toOrderDTO(order);
  }

  public Order findById(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Order id %d is not found", id)));
    return order;
  }

  public List<OrderDTO> findAll() {
    return orderRepository.findAll().stream().map(courier -> orderMapper.toOrderDTO(courier)).toList();
  }

  public OrderDTO changeOrderCourier(Courier courier, Order order) {
    order.setCourier(courier);
    return orderMapper.toOrderDTO(orderRepository.save(order));
  }

  @Transactional
  public OrderDTO changeOrderStatus(Long orderId, OrderStatus status) {
    Order existingOrder = findById(orderId);
    existingOrder.setStatus(status);
    OrderDTO updatedOrderDTO = orderMapper.toOrderDTO(orderRepository.save(existingOrder));
    if (status == OrderStatus.SUCCESSFUL) {
      restaurantService.incrementSuccessfulOrdersCount(
          existingOrder.getRestaurants().stream().map(or -> or.getExistingRestaurant()).toList());

    }
    return updatedOrderDTO;
  }

  public List<OrderRatingInfo> findOrdersToRate(Long customerId) {
    List<OrderRatingInfo> ordersToRate = orderRepository.findUnratedOrdersByCustomerId(customerId);
    return ordersToRate;
  }

  @Transactional
  public void submitRatings(List<OrderRatingSubmission> submissions) {
    for (OrderRatingSubmission submission : submissions) {
      Order order = findById(submission.id());

      order.setCourierRating(submission.courierRating());
      order.setCourierFeedback(submission.courierFeedback());

      for (OrderRestaurantRatingSubmission restaurantRating : submission.restaurants()) {
        OrderRestaurant orderRestaurant = order.getRestaurants().stream()
            .filter(r -> r.getId().equals(restaurantRating.id()))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("OrderRestaurant not found"));

        orderRestaurant.setRestaurantRating(restaurantRating.restaurantRating());
        orderRestaurant.setRestaurantFeedback(restaurantRating.restaurantFeedback());
      }

      orderRepository.save(order);
    }
  }

  public List<OrderDTO> findOrdersByCourierId(Long courierId) {
    return orderRepository.findNotFinishedOrdersByCourierId(courierId).stream()
        .map(order -> orderMapper.toOrderDTO(order)).toList();
  }

  public List<Tuple> findCourierStats() {
    return orderRepository.findCouriersStats();
  }

  public List<Tuple> findCouriersNumOfSuccessfulOrders() {
    return orderRepository.findCouriersNumOfSuccessfulOrders();
  }

}
