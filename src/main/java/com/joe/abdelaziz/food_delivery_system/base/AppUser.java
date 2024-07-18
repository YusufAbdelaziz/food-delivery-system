package com.joe.abdelaziz.food_delivery_system.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.joe.abdelaziz.food_delivery_system.role.Role;
import com.joe.abdelaziz.food_delivery_system.utiles.validation.NullableEmail;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class AppUser extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @NotBlank
  @Pattern(regexp = "\\d+", message = "Phone number must contain only digits")
  @Size(min = 11)
  @Column(unique = true)
  private String phoneNumber;

  @NotBlank
  @Size(min = 8, message = "The size of password should be at least of 8 characters")
  private String password;

  @NotBlank
  @Size(min = 5, message = "The size of name should be at least of 5 characters")
  private String name;

  @NullableEmail
  @Column(unique = true)
  private String email;

  @JsonProperty(value = "image_url")
  private String imageUrl;

  @OneToOne
  @JoinColumn(name = "role_id")
  private Role role;

}
