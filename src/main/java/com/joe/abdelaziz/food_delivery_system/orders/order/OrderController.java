package com.joe.abdelaziz.food_delivery_system.orders.order;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Order", description = "API for managing orders")
public class OrderController {

  private final OrderService orderService;

  @Operation(summary = "Calculate receipt", description = "Calculates the receipt for an order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Receipt calculated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/receipt")
  public ResponseEntity<OrderDTO> calculateReceipt(
      @RequestBody @Parameter(description = "Order data", required = true) OrderDTO order) {
    return ResponseEntity.ok().body(orderService.calculateReceipt(order));
  }

  @Operation(summary = "Apply promotion code", description = "Applies a promotion code to an order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Promotion applied successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('USER')")
  @PostMapping("/apply-promotion")
  public ResponseEntity<OrderDTO> applyPromotion(
      @RequestBody @Parameter(description = "Order data", required = true) OrderDTO order) {
    return ResponseEntity.ok(orderService.applyPromotionCode(order));
  }

  @Operation(summary = "Place an order", description = "Places a new order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order placed successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('USER')")
  @PostMapping("")
  public ResponseEntity<OrderDTO> placeOrder(
      @RequestBody @Parameter(description = "Order data", required = true) OrderDTO order) {
    return ResponseEntity.ok(orderService.insertOrder(order));
  }

  @Operation(summary = "Get order by ID", description = "Fetches order details by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "404", description = "Order not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<OrderDTO> getById(@PathVariable @Parameter(description = "Order ID", required = true) Long id) {
    return ResponseEntity.ok(orderService.findDTOById(id));
  }

  @Operation(summary = "Change order status", description = "Changes the status of an existing order")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order status updated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'COURIER')")
  @PatchMapping("/change-order-status")
  public ResponseEntity<OrderDTO> changeOrderStatus(
      @RequestBody @Parameter(description = "JSON object with orderId and status", required = true) JsonNode node) {
    if (!node.has("orderId") || !node.has("status")) {
      throw new BusinessLogicException(
          "To change a status of an order to a courier, you have to provide the status and the order id");
    }
    try {
      String statusStr = node.get("status").asText();
      OrderStatus status = OrderStatus.valueOf(statusStr);
      Long orderId = node.get("orderId").asLong();
      return ResponseEntity.ok().body(orderService.changeOrderStatus(orderId, status));
    } catch (IllegalArgumentException e) {
      throw new BusinessLogicException("Unable to parse the status, make sure to provide a valid value please", e);
    }
  }

  @Operation(summary = "Get unrated orders by customer ID", description = "Fetches orders that haven't been rated by the customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Unrated orders retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderRatingInfo.class)))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "404", description = "Customer not found")
  })
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/unrated-orders/by-customer/{id}")
  public ResponseEntity<List<OrderRatingInfo>> getUnratedOrders(
      @PathVariable @Parameter(description = "Customer ID", required = true) Long id) {
    return ResponseEntity.ok().body(orderService.findOrdersToRate(id));
  }

  @Operation(summary = "Rate orders by customer ID", description = "Submits ratings for orders by the customer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Orders rated successfully"),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid request data")
  })
  @PreAuthorize("hasRole('USER')")
  @PatchMapping("/rate-orders/by-customer/{id}")
  public ResponseEntity<Void> rateOrders(
      @RequestBody @Parameter(description = "List of order ratings", required = true) List<OrderRatingSubmission> submissions,
      @PathVariable @Parameter(description = "Customer ID", required = true) Long id) {
    orderService.submitRatings(submissions);
    return ResponseEntity.ok().build();
  }
}
