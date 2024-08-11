package com.joe.abdelaziz.food_delivery_system.restaurants.section;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.restaurants.menu.Menu;
import com.joe.abdelaziz.food_delivery_system.restaurants.menu.MenuService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SectionService {

  private final SectionRepository sectionRepository;
  private final MenuService menuService;
  private final SectionMapper sectionMapper;

  @Transactional
  public SectionDTO insertSection(SectionDTO sectionDto, long menuId) {

    Section section = sectionMapper.toSection(sectionDto);
    Menu menu = menuService.getById(menuId);
    Section insertedSection = sectionRepository.save(section);
    menu.addSection(insertedSection);

    return sectionMapper.toSectionDTO(insertedSection);

  }

  public Section getById(long id) {
    return sectionRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Section id %d is not found", id)));
  }

  public SectionDTO getDTOById(Long id) {
    return sectionMapper.toSectionDTO(getById(id));
  }

  @Transactional
  public SectionDTO updateSection(SectionDTO dto) {

    // Fetch the existing section from the table
    Section existingSection = getById(dto.getId());

    Section updatedSection = sectionMapper.toUpdatedSection(dto, existingSection);
    // Save the updated section
    return sectionMapper.toSectionDTO(sectionRepository.save(updatedSection));
  }

  public void deleteSection(Long id) {
    Section existingSection = getById(id);

    sectionRepository.delete(existingSection);
  }

}