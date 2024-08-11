package com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOption;
import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOptionDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOptionMapper;

@Mapper(uses = { SpecOptionMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SpecMapper {

  @Autowired
  private SpecOptionMapper specOptionMapper;

  public abstract SpecDTO toSpecDTO(Spec spec);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
  })
  public abstract Spec toSpec(SpecDTO dto);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "options", expression = "java(updateSpecOptions(dto.getOptions(), existingSpec))")
  })
  public abstract Spec toUpdatedSpec(SpecDTO dto, @MappingTarget Spec existingSpec);

  public Set<SpecOption> updateSpecOptions(Set<SpecOptionDTO> optionsDTOs, Spec existingSpec) {
    if (optionsDTOs == null) {
      return existingSpec.getOptions();
    }

    Set<SpecOption> existingSpecOptions = existingSpec.getOptions();
    if (existingSpecOptions == null) {
      existingSpecOptions = new HashSet<>();
      existingSpec.setOptions(existingSpecOptions);
    }

    Map<Long, SpecOption> existingSpecOptionMap = existingSpecOptions.stream()
        .collect(Collectors.toMap(SpecOption::getId, Function.identity()));

    Set<SpecOption> newOptions = new HashSet<>();

    for (SpecOptionDTO specOptionDTO : optionsDTOs) {
      SpecOption specOption;
      if (specOptionDTO.getId() != null && existingSpecOptionMap.containsKey(specOptionDTO.getId())) {
        specOption = existingSpecOptionMap.get(specOptionDTO.getId());
        specOptionMapper.toUpdatedSpecOption(specOptionDTO, specOption);
      } else {
        specOption = specOptionMapper.toSpecOption(specOptionDTO);
        newOptions.add(specOption);
      }
    }

    existingSpecOptions.addAll(newOptions);

    return existingSpecOptions;
  }
}