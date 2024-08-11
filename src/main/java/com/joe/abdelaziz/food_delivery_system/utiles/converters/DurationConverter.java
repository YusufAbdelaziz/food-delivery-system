package com.joe.abdelaziz.food_delivery_system.utiles.converters;

import java.time.Duration;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Duration duration) {
    return duration == null ? null : (int) duration.getSeconds();
  }

  @Override
  public Duration convertToEntityAttribute(Integer seconds) {
    return seconds == null ? null : Duration.ofSeconds(seconds);
  }
}
