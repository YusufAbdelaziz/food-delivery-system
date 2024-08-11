package com.joe.abdelaziz.food_delivery_system.restaurants.deliveryFee;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/delivery-fees")
@RequiredArgsConstructor
@Validated
@Tag(name = "Delivery Fee", description = "API for managing delivery fees")
public class DeliveryFeeController {

  private final DeliveryFeeService deliveryFeeService;

  @Operation(summary = "Create a new delivery fee", description = "Adds a new delivery fee to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delivery fee created successfully", content = @Content(schema = @Schema(implementation = DeliveryFeeDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<DeliveryFeeDTO> createDeliveryFee(
      @Valid @RequestBody @Parameter(description = "Delivery fee data", required = true) DeliveryFeeDTO deliveryFee) {
    return ResponseEntity.ok().body(deliveryFeeService.insertDeliveryFee(deliveryFee));
  }

  @Operation(summary = "Get delivery fee by ID", description = "Fetches details of a specific delivery fee by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delivery fee retrieved successfully", content = @Content(schema = @Schema(implementation = DeliveryFeeDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "404", description = "Delivery fee not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<DeliveryFeeDTO> getDeliveryFeeById(
      @PathVariable @Parameter(description = "Delivery Fee ID", required = true) Long id) {
    return ResponseEntity.ok().body(deliveryFeeService.findDTOById(id));
  }

  @Operation(summary = "Get all delivery fees", description = "Retrieves a list of all delivery fees")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Delivery fees retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeliveryFeeDTO.class)))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<DeliveryFeeDTO>> getAllDeliveryFees() {
    return ResponseEntity.ok().body(deliveryFeeService.findAll());
  }

}
