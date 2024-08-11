package com.joe.abdelaziz.food_delivery_system.utiles.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NullableRatingValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableRating {
  String message() default "Rating may be null or between 1 and 5";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
