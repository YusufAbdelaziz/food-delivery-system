package com.joe.abdelaziz.food_delivery_system.customer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import com.joe.abdelaziz.food_delivery_system.address.AddressDTO;
import com.joe.abdelaziz.food_delivery_system.address.AddressMapper;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleMapper;

@Mapper(uses = { AddressMapper.class, RoleMapper.class })
public abstract class CustomerMapper {
  @Autowired
  AddressMapper addressMapper;

  @Mapping(target = "activeAddress", ignore = true)

  public abstract OrderCustomerDTO toOrderCustomerDTO(Customer customer);

  @Mappings(value = {

      @Mapping(target = "createdBy", ignore = true),
      @Mapping(target = "createdDate", ignore = true),
      @Mapping(target = "lastModifiedBy", ignore = true),
      @Mapping(target = "lastModifiedDate", ignore = true),
      @Mapping(target = "role", ignore = true),
      @Mapping(target = "password", ignore = true),
      @Mapping(target = "userPromotions", ignore = true),
      @Mapping(target = "orders", ignore = true),
      @Mapping(target = "addresses", ignore = true),
      @Mapping(target = "authorities", ignore = true)

  })
  public abstract Customer toCustomer(OrderCustomerDTO orderCustomerDTO);

  @AfterMapping
  protected void afterMappingToOrderCustomerDTO(Customer customer, @MappingTarget OrderCustomerDTO orderCustomerDTO) {
    if (customer.getAddresses() != null) {
      customer.getAddresses().stream().forEach((address) -> {
        AddressDTO addressDto = addressMapper.toAddressDto(address);
        if (address.getActive()) {
          orderCustomerDTO.setActiveAddress(addressDto);
        }
      });
    }
  }

  @AfterMapping
  protected void afterMappingToCustomer(OrderCustomerDTO orderCustomerDTO, @MappingTarget Customer customer) {
    if (orderCustomerDTO.getActiveAddress() != null) {

      customer.addAddress(addressMapper.toAddress(orderCustomerDTO.getActiveAddress()));
      customer.setActiveAddress(addressMapper.toAddress(orderCustomerDTO.getActiveAddress()));

    }
  }

}
