package com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.abdelaziz.food_delivery_system.restaurants.item.Item;
import com.joe.abdelaziz.food_delivery_system.restaurants.item.ItemService;
import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOptionDTO;
import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOptionMapper;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpecService {

  private final SpecRepository specRepository;
  private final ItemService itemService;
  private final SpecMapper specMapper;
  private final SpecOptionMapper specOptionMapper;

  @Transactional
  public SpecDTO insertSpec(SpecDTO dto, Long itemId) {
    Spec spec = specMapper.toSpec(dto);
    Item item = itemService.getById(itemId);

    Spec savedSpec = specRepository.save(spec);
    item.addSpec(savedSpec);
    return specMapper.toSpecDTO(savedSpec);
  }

  @Transactional
  public SpecDTO insertSpecOptions(Long specId, List<SpecOptionDTO> options) {

    Spec existingSpec = getById(specId);
    existingSpec.addOptions(options.stream().map(option -> specOptionMapper.toSpecOption(option)).toList());
    Spec savedSpec = specRepository.saveAndFlush(existingSpec);
    return specMapper.toSpecDTO(savedSpec);
  }

  public SpecDTO updateSpec(SpecDTO dto) {

    // Fetch the existing section from the table
    Spec existingSpec = getById(dto.getId());

    Spec updatedSpec = specMapper.toUpdatedSpec(dto, existingSpec);
    // Save the updated section
    return specMapper.toSpecDTO(specRepository.save(updatedSpec));
  }

  public void deleteSpec(Long id) {
    Spec existingSpec = getById(id);
    specRepository.delete(existingSpec);
  }

  public Spec getById(Long id) {
    return specRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Spec id %d is not found", id)));
  }

  public SpecDTO getDTOById(Long id) {
    return specMapper.toSpecDTO(getById(id));
  }
}
