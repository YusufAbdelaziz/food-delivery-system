package com.joe.abdelaziz.food_delivery_system.utiles.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NullableRatingValidator implements ConstraintValidator<NullableRating, Byte> {

  @Override
  public boolean isValid(Byte value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return value >= 1 && value <= 5;
  }

}
