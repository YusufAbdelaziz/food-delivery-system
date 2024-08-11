package com.joe.abdelaziz.food_delivery_system.customer;

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

import com.joe.abdelaziz.food_delivery_system.base.AppUser;

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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Customer", description = "API for managing customers")
public class CustomerController {

  private final CustomerService customerService;

  @Operation(summary = "Create a new customer", description = "Creates a new customer and returns the created customer details")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer created successfully", content = @Content(schema = @Schema(implementation = Customer.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PostMapping("")
  public ResponseEntity<Customer> createCustomer(
      @RequestBody @Valid @Parameter(description = "Customer details", required = true) Customer user) {
    log.info("Request received: " + user);
    return ResponseEntity.ok().body(customerService.insertUser(user));
  }

  @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Customer found", content = @Content(schema = @Schema(implementation = AppUser.class))),
      @ApiResponse(responseCode = "404", description = "Customer not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<AppUser> getCustomerById(
      @PathVariable @Parameter(description = "Customer ID", required = true) Long id) {
    return ResponseEntity.ok().body(customerService.findById(id));
  }

  @Operation(summary = "Get all customers", description = "Retrieves a list of all customers")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of customers retrieved successfully", content = @Content(schema = @Schema(implementation = Customer.class), array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
      @ApiResponse(responseCode = "403", description = "Access denied")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    return ResponseEntity.ok().body(customerService.findAll());
  }
}
