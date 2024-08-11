package com.joe.abdelaziz.food_delivery_system.promotions.promotion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Promotion", description = "API for managing promotions")
public class PromotionController {
  private final PromotionService promotionService;

  @Operation(summary = "Insert a new promotion", description = "Creates a new promotion")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Promotion created successfully", content = @Content(schema = @Schema(implementation = PromotionDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<PromotionDTO> insertPromotion(
      @RequestBody @Parameter(description = "Promotion data", required = true) PromotionDTO promotion) {
    return ResponseEntity.ok().body(promotionService.insertPromotion(promotion));
  }

  @Operation(summary = "Get all promotions", description = "Fetches a list of all promotions")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Promotions retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PromotionDTO.class)))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<PromotionDTO>> getAllPromotions() {
    return ResponseEntity.ok().body(promotionService.getAll());
  }

  @Operation(summary = "Deactivate a promotion", description = "Deactivates an existing promotion by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Promotion deactivated successfully", content = @Content(schema = @Schema(implementation = PromotionDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "404", description = "Promotion not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PatchMapping("/{id}")
  public ResponseEntity<PromotionDTO> deactivatePromotion(
      @PathVariable @Parameter(description = "Promotion ID", required = true) Long id) {
    return ResponseEntity.ok().body(promotionService.deactivatePromotion(id));
  }

  @Operation(summary = "Update a promotion", description = "Updates the details of an existing promotion")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Promotion updated successfully", content = @Content(schema = @Schema(implementation = PromotionDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "404", description = "Promotion not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("")
  public ResponseEntity<PromotionDTO> updatePromotion(
      @RequestBody @Parameter(description = "Updated promotion data", required = true) PromotionDTO dto) {
    return ResponseEntity.ok().body(promotionService.updatePromotion(dto));
  }

  @Operation(summary = "Delete a promotion", description = "Deletes a promotion by ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Promotion deleted successfully"),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "404", description = "Promotion not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePromotion(
      @PathVariable @Parameter(description = "Promotion ID", required = true) Long id) {
    promotionService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
