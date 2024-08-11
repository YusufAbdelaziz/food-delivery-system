package com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee;

import java.util.List;

import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.region.Region;
import com.joe.abdelaziz.food_delivery_system.region.RegionService;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.RecordNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryFeeService {

  private final DeliveryFeeRepository deliveryFeeRepository;
  private final RegionService regionService;
  private final DeliveryFeeMapper deliveryFeeMapper;

  public DeliveryFeeDTO insertDeliveryFee(DeliveryFeeDTO dto) {
    DeliveryFee deliveryFee = deliveryFeeMapper.toDeliveryFee(dto);
    Region toRegion = regionService.findRegionById(deliveryFee.getToRegion().getId());
    Region fromRegion = regionService.findRegionById(deliveryFee.getFromRegion().getId());

    deliveryFee.setToRegion(toRegion);
    deliveryFee.setFromRegion(fromRegion);

    return deliveryFeeMapper.toDeliveryDTO(deliveryFeeRepository.save(deliveryFee));
  }

  public DeliveryFee findDeliveryFeeBetweenTwoRegions(Region toRegion, Region fromRegion) {
    Region foundToRegion = regionService.findRegionById(toRegion.getId());
    Region foundFromRegion = regionService.findRegionById(fromRegion.getId());

    return deliveryFeeRepository.findByToRegionAndFromRegion(foundToRegion, foundFromRegion)
        .orElseThrow(() -> new BusinessLogicException(
            String.format("No Delivery fee is found between %d and %d", toRegion.getId(), fromRegion.getId())));
  }

  public DeliveryFee findById(Long id) {
    return deliveryFeeRepository.findById(id)
        .orElseThrow(() -> new RecordNotFoundException(String.format("Delivery Fee of id %d is not found", id)));
  }

  public DeliveryFeeDTO findDTOById(Long id) {
    return deliveryFeeMapper.toDeliveryDTO(findById(id));
  }

  public List<DeliveryFeeDTO> findAll() {
    return deliveryFeeRepository.findAll().stream().map(fee -> deliveryFeeMapper.toDeliveryDTO(fee)).toList();
  }

}
