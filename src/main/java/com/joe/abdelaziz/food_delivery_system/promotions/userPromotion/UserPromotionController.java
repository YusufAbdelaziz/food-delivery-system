package com.joe.abdelaziz.food_delivery_system.promotions.userPromotion;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user-promotions")
@RequiredArgsConstructor
@Validated
@Tag(name = "UserPromotion", description = "API for managing user-specific promotions")
public class UserPromotionController {
  private final UserPromotionService userPromotionService;

  @Operation(summary = "Get all user promotions", description = "Fetches all user-specific promotions")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User promotions retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserPromotionDTO.class)))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<UserPromotionDTO>> getAllUserPromotions() {
    return ResponseEntity.ok().body(userPromotionService.getAll());
  }

}
