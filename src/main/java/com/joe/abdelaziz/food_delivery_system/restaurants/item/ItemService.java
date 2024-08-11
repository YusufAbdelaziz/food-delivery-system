package com.joe.abdelaziz.food_delivery_system.restaurants.item;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.SpecDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.SpecMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.section.Section;
import com.joe.abdelaziz.food_delivery_system.restaurants.section.SectionService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;
  private final SectionService sectionService;
  private final ItemMapper itemMapper;
  private final SpecMapper specMapper;

  @Transactional
  public ItemDTO insertItem(ItemDTO dto, Long sectionId) {

    Section section = sectionService.getById(sectionId);
    Item item = itemMapper.toItem(dto);

    Item savedItem = itemRepository.save(item);
    section.addItem(savedItem);
    return itemMapper.toItemDTO(savedItem);
  }

  @Transactional
  public ItemDTO insertSpecs(Long itemId, List<SpecDTO> specs) {

    Item existingItem = getById(itemId);
    existingItem.addSpecs(specs.stream().map(spec -> specMapper.toSpec(spec)).toList());
    Item savedItem = itemRepository.saveAndFlush(existingItem);
    return itemMapper.toItemDTO(savedItem);
  }

  public ItemDTO updateItem(ItemDTO dto) {

    Item existingItem = getById(dto.getId());

    Item updatedItem = itemMapper.toUpdatedItem(dto, existingItem);
    // Save the updated section
    return itemMapper.toItemDTO(itemRepository.save(updatedItem));
  }

  public void deleteItem(Long id) {
    itemRepository.deleteById(id);
  }

  public Item getById(Long id) {

    return itemRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Item id %d is not found", id)));
  }

  public ItemDTO getDTOById(Long id) {
    return itemMapper.toItemDTO(getById(id));
  }
}
