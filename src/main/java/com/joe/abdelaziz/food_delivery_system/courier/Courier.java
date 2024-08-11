package com.joe.abdelaziz.food_delivery_system.courier;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.security.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "courier")
@Getter
@Setter
@Slf4j
public class Courier extends BaseEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "courier_id")
  private Long id;

  @NotBlank
  @Size(min = 11)
  @Column(unique = true)
  private String phoneNumber;

  @NotBlank
  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  @OneToOne
  @JoinColumn(name = "role_id")
  private Role role;

  private boolean active;

  private BigDecimal avgRating;

  private BigDecimal earnings;

  private int successfulOrders;

  @OneToMany(mappedBy = "courier")
  @JsonIgnoreProperties("courier")
  private Set<Order> orders = new HashSet<>();

  public void addOrder(Order order) {
    orders.add(order);
  }

  public void removeOrder(Long orderId) {
    boolean isRemoved = orders.removeIf(order -> order.getId().equals(orderId));
    if (!isRemoved) {
      log.error(String.format("An order of order id %d is not removed from the courier", orderId));
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getType().getAuthorities();
  }

  @Override
  public String getUsername() {
    return phoneNumber;
  }

}
