package com.joe.abdelaziz.food_delivery_system.courier;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.joe.abdelaziz.food_delivery_system.orders.order.OrderDTO;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourierDTO {

  private Long id;

  @NotBlank
  @Size(min = 11)
  private String phoneNumber;

  @NotBlank
  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  private RoleDTO role;

  private boolean active;

  private BigDecimal avgRating;

  private BigDecimal earnings;

  private int successfulOrders;

  private Set<OrderDTO> orders = new HashSet<>();

  public void addOrder(OrderDTO order) {
    orders.add(order);
  }
}
