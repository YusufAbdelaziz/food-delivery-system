package com.joe.abdelaziz.food_delivery_system.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.admin.AdminService;
import com.joe.abdelaziz.food_delivery_system.courier.CourierService;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
  private CourierService courierService;
  private AdminService adminService;
  private CustomerService customerService;

  @Override
  public UserDetails loadUserByUsername(String roleAndPhoneNumber) throws UsernameNotFoundException {
    // Assume the username is in the format "role:phoneNumber"
    String[] parts = roleAndPhoneNumber.split(":");
    if (parts.length != 2) {
      throw new UsernameNotFoundException("Invalid role and phone number format");
    }

    String role = parts[0].toUpperCase();
    String phoneNumber = parts[1];

    switch (role.toUpperCase()) {
      case "ADMIN":
        return adminService.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Customer not found"));
      case "USER":
        return customerService.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
      case "COURIER":
        return courierService.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new UsernameNotFoundException("Courier not found"));
      default:
        throw new UsernameNotFoundException("Invalid role");
    }
  }

}
