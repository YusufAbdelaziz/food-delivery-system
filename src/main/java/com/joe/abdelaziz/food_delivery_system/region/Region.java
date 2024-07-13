package com.joe.abdelaziz.food_delivery_system.region;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joe.abdelaziz.food_delivery_system.address.Address;
import com.joe.abdelaziz.food_delivery_system.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Region extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "region_id")
  private Long id;

  @NotBlank
  @Size(max = 100, message = "You should provide a region name of length 100 character at most")
  private String name;

  @OneToMany(mappedBy = "region", fetch = FetchType.EAGER)
  @JsonIgnoreProperties("region")
  private Set<Address> addresses;

}
