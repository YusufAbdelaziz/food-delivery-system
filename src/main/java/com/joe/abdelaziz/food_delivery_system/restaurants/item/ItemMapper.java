package com.joe.abdelaziz.food_delivery_system.restaurants.item;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.Spec;
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.SpecDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.SpecMapper;

@Mapper(uses = { SpecMapper.class })
public abstract class ItemMapper {

  @Autowired
  private SpecMapper specMapper;

  public abstract ItemDTO toItemDTO(Item item);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
  })
  public abstract Item toItem(ItemDTO dto);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "specs", expression = "java(updateSpecs(dto.getSpecs(), existingItem))")
  })
  public abstract Item toUpdatedItem(ItemDTO dto, @MappingTarget Item existingItem);

  public Set<Spec> updateSpecs(Set<SpecDTO> specDTOs, Item existingItem) {
    if (specDTOs == null) {
      return existingItem.getSpecs();
    }

    Set<Spec> existingSpecs = existingItem.getSpecs();
    if (existingSpecs == null) {
      existingSpecs = new HashSet<>();
      existingItem.setSpecs(existingSpecs);
    }

    Map<Long, Spec> existingSpecMap = existingSpecs.stream()
        .collect(Collectors.toMap(Spec::getId, Function.identity()));

    Set<Spec> newSpecs = new HashSet<>();

    for (SpecDTO specDTO : specDTOs) {
      Spec spec;
      if (specDTO.getId() != null && existingSpecMap.containsKey(specDTO.getId())) {
        spec = existingSpecMap.get(specDTO.getId());
        specMapper.toUpdatedSpec(specDTO, spec);
      } else {
        spec = specMapper.toSpec(specDTO);
        newSpecs.add(spec);
      }
    }

    existingSpecs.addAll(newSpecs);

    return existingSpecs;
  }
}