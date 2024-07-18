package com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;
import com.joe.abdelaziz.food_delivery_system.restaurants.item.Item;
import com.joe.abdelaziz.food_delivery_system.restaurants.option.Option;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Spec extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "spec_id")
  private Long id;

  @Enumerated(EnumType.STRING)
  private SpecType type;

  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "spec", orphanRemoval = true)
  @JsonIgnoreProperties("spec")
  private Set<Option> options = new HashSet<>();

}
