package com.joe.abdelaziz.food_delivery_system.courier;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.role.Role;
import com.joe.abdelaziz.food_delivery_system.role.RoleService;
import com.joe.abdelaziz.food_delivery_system.role.RoleType;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourierService {

  private final CourierRepository courierRepository;

  private final RoleService roleService;

  public Courier insertCourier(Courier courier) {
    Role role = roleService.findRoleByType(RoleType.COURIER);
    courier.setRole(role);

    Courier insertedCourier = courierRepository.save(courier);
    return insertedCourier;
  }

  public Courier findCourierById(Long id) {
    return courierRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Courier id %d is not found", id)));
  }

  public List<Courier> findAll() {
    return courierRepository.findAll();
  }

}
