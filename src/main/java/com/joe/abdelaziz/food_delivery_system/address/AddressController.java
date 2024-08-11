package com.joe.abdelaziz.food_delivery_system.address;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Address", description = "API for managing user addresses")
public class AddressController {

  private final AddressService addressService;
  private final ObjectMapper objectMapper;

  @Operation(summary = "Add a new address", description = "Adds a new address for a user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Address created successfully", content = @Content(schema = @Schema(implementation = AddressDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('USER')")
  @PostMapping("")
  public ResponseEntity<AddressDTO> addAddress(
      @Valid @RequestBody @Parameter(description = "JSON object with userId and address", required = true) JsonNode node) {
    if (!node.has("userId") || !node.has("address")) {
      throw new BusinessLogicException("You should provide a userId and address to insert a new address");
    }

    try {
      AddressDTO addressDTO = objectMapper.treeToValue(node.get("address"), AddressDTO.class);
      Long userId = node.get("userId").asLong();
      return ResponseEntity.status(HttpStatus.CREATED).body(addressService.insertAddress(addressDTO, userId));
    } catch (JsonProcessingException | IllegalArgumentException e) {
      log.error("Could not parse userId and address fields", e);
      throw new BusinessLogicException("Could not parse userId and address fields");
    }
  }

  @Operation(summary = "Get address by ID", description = "Fetches an address by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Address retrieved successfully", content = @Content(schema = @Schema(implementation = AddressDTO.class))),
      @ApiResponse(responseCode = "404", description = "Address not found")
  })
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<AddressDTO> getAddressById(
      @PathVariable @Parameter(description = "Address ID", required = true) Long id) {
    return ResponseEntity.ok().body(addressService.findDTOById(id));
  }

  @Operation(summary = "Get all addresses by customer ID", description = "Fetches all addresses associated with a customer ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDTO.class)))),
      @ApiResponse(responseCode = "404", description = "Customer not found")
  })
  @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
  @GetMapping("/by-customer/{id}")
  public ResponseEntity<List<AddressDTO>> getAllAddressesByUserId(
      @PathVariable @Parameter(description = "Customer ID", required = true) Long id) {
    return ResponseEntity.ok().body(addressService.findAddressesByCustomerId(id));
  }

  @Operation(summary = "Activate an address", description = "Sets the status of an address to active")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Address status updated successfully", content = @Content(schema = @Schema(implementation = AddressDTO.class))),
      @ApiResponse(responseCode = "404", description = "Address not found"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("/{addressId}")
  public ResponseEntity<AddressDTO> changeAddressActivityToTrue(
      @PathVariable @Parameter(description = "Address ID", required = true) Long addressId) {
    return ResponseEntity.ok().body(addressService.setAddressToActive(addressId));
  }

  @Operation(summary = "Update an address", description = "Updates an existing address")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Address updated successfully", content = @Content(schema = @Schema(implementation = AddressDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid request data"),
      @ApiResponse(responseCode = "404", description = "Address not found"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasAnyRole('USER')")
  @PutMapping("")
  public ResponseEntity<AddressDTO> updateAddress(
      @RequestBody @Parameter(description = "Address data", required = true) AddressDTO addressDTO) {
    return ResponseEntity.ok().body(addressService.updateAddress(addressDTO));
  }

  @Operation(summary = "Delete an address", description = "Deletes an address by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Address not found"),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasAnyRole('USER')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAddress(
      @PathVariable @Parameter(description = "Address ID", required = true) long id) {
    addressService.deleteAddress(id);
    return ResponseEntity.noContent().build();
  }
}
