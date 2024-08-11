package com.joe.abdelaziz.food_delivery_system.restaurants.specOption;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.Spec;
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.SpecService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecOptionService {

  private final SpecOptionRepository specOptionRepository;
  private final SpecService specService;
  private final SpecOptionMapper specOptionMapper;

  @Transactional
  public SpecOptionDTO insertSpecOption(SpecOptionDTO dto, Long specId) {
    SpecOption specOption = specOptionMapper.toSpecOption(dto);
    Spec existingSpec = specService.getById(specId);

    SpecOption newOption = specOptionRepository.save(specOption);
    existingSpec.addOption(newOption);

    return specOptionMapper.toSpecOptionDTO(newOption);
  }

  public SpecOption updateSpecOption(SpecOption updatedOption) {

    SpecOption existingOption = getById(updatedOption.getId());

    if (updatedOption.getName() != null)
      existingOption.setName(updatedOption.getName());
    if (updatedOption.getPrice() != null)
      existingOption.setPrice(updatedOption.getPrice());

    return specOptionRepository.save(existingOption);
  }

  public void deleteItem(Long id) {
    SpecOption existingSpecOption = getById(id);
    specOptionRepository.delete(existingSpecOption);
  }

  public SpecOption getById(Long id) {
    if (id == null) {
      throw new BusinessLogicException("Spec option id is null");
    }
    return specOptionRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Spec option id %d is not found", id)));
  }

}
