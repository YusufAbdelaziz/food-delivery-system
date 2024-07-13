package com.joe.abdelaziz.food_delivery_system.utiles.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContextConfig {

  @Bean
  public CycleAvoidingMappingContext cycleAvoidingMappingContext() {
    return new CycleAvoidingMappingContext();
  }
}
