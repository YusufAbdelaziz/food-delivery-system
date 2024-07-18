package com.joe.abdelaziz.food_delivery_system.restaurants.section;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.item.Item;
import com.joe.abdelaziz.food_delivery_system.restaurants.menu.Menu;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Section extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "section_id")
  private Long id;

  @NotBlank
  @Size(min = 5, message = "Section name should be at least 5 characters in length")
  private String name;

  @ManyToOne
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Valid
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "section", orphanRemoval = true)
  @JsonIgnoreProperties("section")
  private Set<Item> items = new HashSet<>();
}
