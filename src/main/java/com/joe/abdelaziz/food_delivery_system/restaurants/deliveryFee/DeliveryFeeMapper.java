package com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.joe.abdelaziz.food_delivery_system.region.RegionMapper;

@Mapper(uses = { RegionMapper.class })
public abstract class DeliveryFeeMapper {

  public abstract DeliveryFeeDTO toDeliveryDTO(DeliveryFee deliveryFee);

  @Mappings(value = {

      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
  })
  public abstract DeliveryFee toDeliveryFee(DeliveryFeeDTO dto);

  @Mappings(value = {
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),

  })
  public abstract DeliveryFee toUpdatedDeliveryFee(DeliveryFeeDTO dto, @MappingTarget DeliveryFee existingDeliveryFee);

}
