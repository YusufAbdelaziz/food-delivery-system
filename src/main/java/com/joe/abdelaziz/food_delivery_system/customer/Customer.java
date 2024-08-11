package com.joe.abdelaziz.food_delivery_system.customer;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.base.AppUser;
import com.joe.abdelaziz.food_delivery_system.orders.order.Order;
import com.joe.abdelaziz.food_delivery_system.promotions.userPromotion.UserPromotion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DiscriminatorValue("USER")
public class Customer extends AppUser {

  @OneToMany(mappedBy = "customer")
  @JsonIgnore
  private Set<Order> orders = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", orphanRemoval = true)
  @JsonIgnoreProperties({ "customer", "restaurant" })
  private Set<Address> addresses = new HashSet<>();

  @OneToMany(mappedBy = "customer")
  @JsonIgnoreProperties("userPromotions")
  @JsonIgnore
  private Set<UserPromotion> userPromotions = new HashSet<>();

  @Transient
  @JsonIgnoreProperties({ "customer" })
  private Address activeAddress;

  public void addAddress(Address address) {
    addresses.add(address);
    address.setCustomer(this);
  }

  public void removeAddress(Address address) {
    addresses.remove(address);
    address.setCustomer(null);
  }

  @PostLoad
  private void assignActiveAddress() {
    for (Address address : addresses) {
      if (address.getActive())
        activeAddress = address;
    }
  }
}
