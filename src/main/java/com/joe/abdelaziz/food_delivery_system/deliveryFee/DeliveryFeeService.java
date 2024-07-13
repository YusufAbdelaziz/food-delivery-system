package com.joe.abdelaziz.food_delivery_system.deliveryFee;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.region.Region;
// import com.joe.abdelaziz.food_delivery_system.region.RegionMapper;
import com.joe.abdelaziz.food_delivery_system.region.RegionService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryFeeService {

  private final DeliveryFeeRepository deliveryFeeRepository;

  private final RegionService regionService;

  /**
   * Inserts a new user into the system. Note that the user will probably add a
   * single address after registration.
   *
   * @param user the user object to be inserted
   * @return the inserted user object
   */
  public DeliveryFee insertDeliveryFee(DeliveryFee deliveryFee) {
    Region toRegion = regionService.findRegionById(deliveryFee.getToRegion().getId());
    Region fromRegion = regionService.findRegionById(deliveryFee.getFromRegion().getId());

    deliveryFee.setToRegion(toRegion);
    deliveryFee.setFromRegion(fromRegion);

    return deliveryFeeRepository.save(deliveryFee);
  }

  public DeliveryFee findDeliveryFeeById(Long id) {
    return deliveryFeeRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Delivery Fee of id %d is not found", id)));
  }

  public List<DeliveryFee> findAll() {
    return deliveryFeeRepository.findAll();
  }

}
