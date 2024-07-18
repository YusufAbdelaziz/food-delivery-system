package com.joe.abdelaziz.food_delivery_system.restaurants.menu;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.section.Section;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Menu extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "menu_id")
  private Long id;

  @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
  private Restaurant restaurant;

  @Valid
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "menu", orphanRemoval = true)
  @JsonIgnoreProperties("menu")
  private Set<Section> sections = new HashSet<>();
}
