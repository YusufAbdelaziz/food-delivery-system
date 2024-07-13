package com.joe.abdelaziz.food_delivery_system.admin;

import com.joe.abdelaziz.food_delivery_system.base.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * The Admin class represents an admin user.
 * It extends the AppUser class and adds additional functionality specific to an
 * admin user.
 */
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;
}
