package com.joe.abdelaziz.food_delivery_system.restaurants.item;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.Spec;
import com.joe.abdelaziz.food_delivery_system.restaurants.section.Section;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Item extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;

  @Size(min = 5, message = "Item name should be at least 5 characters in length")
  private String name;

  @Positive
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "section_id")
  @JsonIgnoreProperties("items")
  private Section section;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "item", orphanRemoval = true)
  @JsonIgnoreProperties("section")
  private Set<Spec> specs = new HashSet<>();

}
