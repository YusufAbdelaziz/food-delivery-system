package com.joe.abdelaziz.food_delivery_system.security.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

  ADMIN_READ("admin:read"),
  ADMIN_UPDATE("admin:update"),
  ADMIN_CREATE("admin:create"),
  ADMIN_DELETE("admin:delete"),
  CUSTOMER_READ("customer:read"),
  CUSTOMER_UPDATE("customer:update"),
  CUSTOMER_CREATE("customer:create"),
  CUSTOMER_DELETE("customer:delete"),
  COURIER_READ("courier:read"),
  COURIER_UPDATE("courier:update"),
  COURIER_CREATE("courier:create"),
  COURIER_DELETE("courier:delete");

  @Getter
  private final String permission;
}