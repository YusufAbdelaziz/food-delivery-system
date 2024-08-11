package com.joe.abdelaziz.food_delivery_system.security.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.joe.abdelaziz.food_delivery_system.base.AppUser;
import com.joe.abdelaziz.food_delivery_system.courier.Courier;

public class ApplicationAuditAware implements AuditorAware<String> {
  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder
        .getContext()
        .getAuthentication();
    if (authentication == null ||
        !authentication.isAuthenticated() ||
        authentication instanceof AnonymousAuthenticationToken) {
      return Optional.empty();
    }

    if (authentication.getPrincipal() instanceof AppUser) {

      AppUser user = (AppUser) authentication.getPrincipal();
      return Optional.ofNullable(user.getPhoneNumber());
    } else if (authentication.getPrincipal() instanceof Courier) {
      Courier courier = (Courier) authentication.getPrincipal();
      return Optional.ofNullable(courier.getPhoneNumber());
    }

    return Optional.empty();

  }
}