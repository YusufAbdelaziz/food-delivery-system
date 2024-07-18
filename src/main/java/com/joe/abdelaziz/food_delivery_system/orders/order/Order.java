package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.courier.Courier;
import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.orders.orderRestaurant.OrderRestaurant;
import com.joe.abdelaziz.food_delivery_system.promotions.promotion.Promotion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "order_id")
  private Long id;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  private Customer customer;

  @ManyToOne
  @JoinColumn(name = "promotion_id", nullable = true)
  private Promotion promotion;

  @OneToMany(mappedBy = "order")
  private Set<OrderRestaurant> orderRestaurants = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "courier_id")
  @JsonIgnoreProperties("orders")
  private Courier courier;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Transient
  private Address activeCustomerAddress;

  private LocalDateTime estimatedDeliveryDate;

  @Null
  @Positive
  @Max(value = 5)
  @Min(value = 1)
  private byte orderRating;

  @Null
  @Positive
  @Max(value = 5, message = "Max value of rating is 5")
  @Min(value = 1, message = "Min value of rating is 1")
  private byte courierRating;

  @Null
  @Size(max = 2000, message = "Restaurant feedback should be at least 2000 characters in length")
  private String restaurantFeedback;

  @Null
  @Size(max = 2000, message = "Courier feedback should be at least 2000 characters in length")
  private String courierFeedback;

  private BigDecimal orderTotal;

  @PostLoad
  private void populateActiveAddress() {
    this.activeCustomerAddress = customer.getActiveAddress();
  }
}
