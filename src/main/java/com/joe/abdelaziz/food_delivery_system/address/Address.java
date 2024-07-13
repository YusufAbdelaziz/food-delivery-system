package com.joe.abdelaziz.food_delivery_system.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.joe.abdelaziz.food_delivery_system.base.AppUser;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.region.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  private Long id;

  private double latitude;

  private double longitude;

  private String description;

  @ManyToOne()
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties("addresses")
  private AppUser user;

  // TODO : Add a restaurant's address

  @ManyToOne
  @JoinColumn(name = "region_id", nullable = false)
  @JsonIgnoreProperties("addresses")
  private Region region;

  @NotNull
  @Positive(message = "You should put a positive number as your building number")
  private short buildingNumber;

  @NotNull
  @Positive(message = "You should put a positive number as your apartment number")
  private short apartmentNumber;

  @NotNull
  @Column(name = "active")
  private boolean active;
}
