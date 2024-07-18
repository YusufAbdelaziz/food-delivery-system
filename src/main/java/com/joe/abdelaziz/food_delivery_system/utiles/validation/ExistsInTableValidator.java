package com.joe.abdelaziz.food_delivery_system.utiles.validation;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ExistsInTableValidator implements ConstraintValidator<ExistsInTable, Long> {

  @Autowired
  private EntityManager entityManager;

  private Class<?> entityClass;

  @Override
  public void initialize(ExistsInTable constraintAnnotation) {
    this.entityClass = constraintAnnotation.entity();
  }

  @Override
  public boolean isValid(Long id, ConstraintValidatorContext context) {
    if (id == null) {
      return false;
    }
    return entityManager.find(entityClass, id) != null;
  }

}
