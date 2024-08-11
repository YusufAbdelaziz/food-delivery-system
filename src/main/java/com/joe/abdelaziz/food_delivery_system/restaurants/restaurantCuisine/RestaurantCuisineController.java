package com.joe.abdelaziz.food_delivery_system.restaurants.restaurantCuisine;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api/v1/restaurant-cuisines")
@RequiredArgsConstructor
@Validated
@Tag(name = "Restaurant-Cuisine", description = "API for managing the association between restaurants and cuisines")
public class RestaurantCuisineController {

  private final RestaurantCuisineService restaurantCuisineService;

  @Operation(summary = "Assign a restaurant to a cuisine", description = "Creates a new association between a restaurant and a cuisine")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurant assigned to cuisine successfully", content = @Content(schema = @Schema(implementation = RestaurantCuisineDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<RestaurantCuisineDTO> assignRestaurantToCuisine(
      @RequestBody @Parameter(description = "Restaurant-Cuisine association data", required = true) @Valid RestaurantCuisineDTO restaurantCuisine) {
    RestaurantCuisineDTO insertedAddress = restaurantCuisineService.insert(restaurantCuisine);
    return ResponseEntity.ok().body(insertedAddress);
  }

  @Operation(summary = "Get all restaurant-cuisine associations", description = "Retrieves a list of all restaurant-cuisine associations")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Associations retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantCuisineDTO.class)))),
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<RestaurantCuisineDTO>> getAllRestaurantCuisines() {
    return ResponseEntity.ok().body(restaurantCuisineService.getAll());
  }

  @Operation(summary = "Delete a restaurant-cuisine association", description = "Removes an association between a restaurant and a cuisine")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Association deleted successfully"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("")
  public ResponseEntity<Void> deleteRestaurantCuisineAssociation(
      @RequestBody @Parameter(description = "ID of the association to be deleted", required = true) RestaurantCuisineIdDTO id) {
    restaurantCuisineService.deleteRestaurantCuisine(id);
    return ResponseEntity.noContent().build();
  }
}
