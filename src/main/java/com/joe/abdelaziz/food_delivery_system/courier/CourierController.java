package com.joe.abdelaziz.food_delivery_system.courier;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.joe.abdelaziz.food_delivery_system.orders.order.OrderDTO;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;

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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/couriers")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Courier", description = "API for managing couriers")
public class CourierController {

  private final CourierService courierService;

  @Operation(summary = "Create a new courier", description = "Creates a new courier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courier created successfully", content = @Content(schema = @Schema(implementation = CourierDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  @PostMapping("")
  public ResponseEntity<CourierDTO> createCourier(
      @Valid @RequestBody @Parameter(description = "Courier details", required = true) CourierDTO courier) {
    return ResponseEntity.ok().body(courierService.insertCourier(courier));
  }

  @Operation(summary = "Get courier by ID", description = "Fetches a courier by their ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courier retrieved successfully", content = @Content(schema = @Schema(implementation = CourierDTO.class))),
      @ApiResponse(responseCode = "404", description = "Courier not found"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<CourierDTO> getCourierById(
      @PathVariable @Parameter(description = "Courier ID", required = true) Long id) {
    return ResponseEntity.ok().body(courierService.findDtoById(id));
  }

  @Operation(summary = "Get all couriers", description = "Fetches all couriers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Couriers retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourierDTO.class)))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<CourierDTO>> getAllCouriers() {
    return ResponseEntity.ok().body(courierService.findAll());
  }

  @Operation(summary = "Update a courier", description = "Updates an existing courier's details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courier updated successfully", content = @Content(schema = @Schema(implementation = CourierDTO.class))),
      @ApiResponse(responseCode = "404", description = "Courier not found"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'COURIER')")
  @PutMapping("")
  public ResponseEntity<CourierDTO> updateCourier(
      @RequestBody @Parameter(description = "Updated courier details", required = true) CourierDTO courier) {
    return ResponseEntity.ok().body(courierService.updateCourier(courier));
  }

  @Operation(summary = "Assign a courier to an order", description = "Assigns a courier to an order based on their IDs")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Courier assigned to order successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/assign-courier")
  public ResponseEntity<OrderDTO> assignCourierToOrder(
      @RequestBody @Parameter(description = "Order and Courier IDs", required = true) JsonNode node) {
    if (!node.has("orderId") || !node.has("courierId")) {
      throw new BusinessLogicException(
          "To assign an order to a courier, you have to provide the courier id and the order id");
    }

    Long orderId = node.get("orderId").asLong();
    Long courierId = node.get("courierId").asLong();

    return ResponseEntity.ok().body(courierService.assignOrderToCourier(courierId, orderId));
  }

  @Operation(summary = "Get orders assigned to a courier", description = "Fetches all orders assigned to a courier by their ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDTO.class)))),
      @ApiResponse(responseCode = "404", description = "Courier not found"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'COURIER')")
  @GetMapping("/{id}/orders")
  public ResponseEntity<List<OrderDTO>> findOrdersByCourierId(
      @RequestBody @Parameter(description = "Courier ID", required = true) Long id) {
    return ResponseEntity.ok().body(courierService.findOrdersByCourierId(id));
  }
}
