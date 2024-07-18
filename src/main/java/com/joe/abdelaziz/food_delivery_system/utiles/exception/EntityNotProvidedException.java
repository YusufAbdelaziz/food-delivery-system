package com.joe.abdelaziz.food_delivery_system.utiles.exception;

public class EntityNotProvidedException extends RuntimeException {

  public EntityNotProvidedException() {
    super();
  }

  public EntityNotProvidedException(String message) {
    super(message);
  }

}
