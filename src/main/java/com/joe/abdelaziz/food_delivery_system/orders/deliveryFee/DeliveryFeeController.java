package com.joe.abdelaziz.food_delivery_system.orders.deliveryFee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/delivery-fees")
@AllArgsConstructor
@Validated
public class DeliveryFeeController {

  private final DeliveryFeeService deliveryFeeService;

  @PostMapping("")
  public ResponseEntity<DeliveryFee> createDeliveryFee(@Valid @RequestBody DeliveryFee deliveryFee) {

    return ResponseEntity.ok().body(deliveryFeeService.insertDeliveryFee(deliveryFee));
  }

  @GetMapping("/{id}")
  public ResponseEntity<DeliveryFee> getDeliveryFeeById(@PathVariable Long id) {
    return ResponseEntity.ok().body(deliveryFeeService.findDeliveryFeeById(id));
  }

  @GetMapping("")
  public ResponseEntity<List<DeliveryFee>> getAllDeliveryFees() {
    return ResponseEntity.ok().body(deliveryFeeService.findAll());
  }

}
