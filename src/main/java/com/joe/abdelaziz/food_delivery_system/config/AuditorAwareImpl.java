package com.joe.abdelaziz.food_delivery_system.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;

// TODO: Remove this after configuring security.
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  @NonNull
  public Optional<String> getCurrentAuditor() {
    return Optional.of("Joe");
  }

}
