package com.joe.abdelaziz.food_delivery_system.customer;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.base.AppUser;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.promotions.userPromotion.UserPromotion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("USER")
public class Customer extends AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @OneToMany(mappedBy = "customer")
  private Set<Order> orders = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
  @JoinColumn(name = "active_address_id")
  @JsonIgnoreProperties("customer")
  private Address activeAddress;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
  @JsonIgnoreProperties({ "user", "restaurant" })
  private Set<Address> addresses = new HashSet<>();

  @OneToMany(mappedBy = "customer")
  @JsonIgnoreProperties("userPromotions")
  private Set<UserPromotion> userPromotions = new HashSet<>();
}
