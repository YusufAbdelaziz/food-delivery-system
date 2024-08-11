package com.joe.abdelaziz.food_delivery_system.restaurants.section;

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

import com.joe.abdelaziz.food_delivery_system.restaurants.item.Item;
import com.joe.abdelaziz.food_delivery_system.restaurants.item.ItemDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.item.ItemMapper;

@Mapper(uses = { ItemMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class SectionMapper {

  @Autowired
  private ItemMapper itemMapper;

  public abstract SectionDTO toSectionDTO(Section section);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
  })
  public abstract Section toSection(SectionDTO dto);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "items", expression = "java(updateItems(dto.getItems(), existingSection))")
  })
  public abstract Section toUpdatedSection(SectionDTO dto, @MappingTarget Section existingSection);

  public Set<Item> updateItems(Set<ItemDTO> itemDTOs, Section existingSection) {
    if (itemDTOs == null) {
      return existingSection.getItems();
    }

    Set<Item> existingItems = existingSection.getItems();
    if (existingItems == null) {
      existingItems = new HashSet<>();
      existingSection.setItems(existingItems);
    }

    Map<Long, Item> existingItemMap = existingItems.stream()
        .collect(Collectors.toMap(Item::getId, Function.identity()));

    Set<Item> newItems = new HashSet<>();

    for (ItemDTO itemDTO : itemDTOs) {
      Item item;
      if (itemDTO.getId() != null && existingItemMap.containsKey(itemDTO.getId())) {
        item = existingItemMap.get(itemDTO.getId());
        itemMapper.toUpdatedItem(itemDTO, item);
      } else {
        item = itemMapper.toItem(itemDTO);
        newItems.add(item);
      }
    }

    existingItems.addAll(newItems);

    return existingItems;
  }
}