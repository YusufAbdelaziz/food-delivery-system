package com.joe.abdelaziz.food_delivery_system.restaurants.menu;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.Restaurant;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantMapper;
import com.joe.abdelaziz.food_delivery_system.restaurants.restaurant.RestaurantService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuService {

  private final MenuRepository menuRepository;
  private final RestaurantService restaurantService;
  private final MenuMapper menuMapper;
  private final RestaurantMapper restaurantMapper;

  @Transactional
  public MenuDTO insertMenu(MenuDTO dto) {
    Menu menu = menuMapper.toMenu(dto);
    if (dto.getRestaurant() == null) {
      throw new BusinessLogicException("Restaurant entity should be provided (restaurant_id)");
    }
    Restaurant restaurant = restaurantService.getById(dto.getRestaurant().getId());
    menu.setRestaurant(restaurant);
    Menu insertedMenu = menuRepository.save(menu);
    restaurant.setMenu(insertedMenu);

    return menuMapper.toMenuDTO(insertedMenu);
  }

  public MenuDTO getByRestaurantId(Long id) {
    if (id == null) {
      throw new IllegalArgumentException("Can't fetch a Menu: id is null");
    }
    Menu menu = menuRepository.findByRestaurant(id);
    return menuMapper.toMenuDTO(menu);
  }

  public Menu getById(long id) {
    return menuRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Menu id %d is not found", id)));
  }

  public MenuDTO getDTOById(long id) {
    return menuMapper.toMenuDTO(getById(id));
  }

  public List<MenuDTO> getAll() {
    return menuRepository.findAll().stream().map(menu -> menuMapper.toMenuDTO(menu)).toList();
  }

  @Transactional
  public void deleteMenu(long id) {
    Menu menu = getById(id);

    if (menu.getRestaurant() != null) {
      Restaurant restaurant = menu.getRestaurant();
      restaurant.setMenu(null);
      restaurantService.updateRestaurant(restaurantMapper.toRestaurantDTO(restaurant));
    }

    menuRepository.delete(menu);
  }
}
