package com.joe.abdelaziz.food_delivery_system.courier;

import java.math.BigDecimal;

import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courier")
@Getter
@Setter
public class Courier extends BaseEntity {
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

}
