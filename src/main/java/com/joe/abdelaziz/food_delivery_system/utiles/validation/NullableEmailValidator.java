package com.joe.abdelaziz.food_delivery_system.utiles.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NullableEmailValidator implements ConstraintValidator<NullableEmail, String> {

  private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

  private Pattern pattern = Pattern.compile(EMAIL_PATTERN);

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return true; // Allow null and empty values
    }
    return pattern.matcher(value).matches();
  }
}
