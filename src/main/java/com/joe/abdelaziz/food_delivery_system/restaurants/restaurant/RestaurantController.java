package com.joe.abdelaziz.food_delivery_system.restaurants.restaurant;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joe.abdelaziz.food_delivery_system.utiles.customResponses.UpdateResponse;

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
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Validated
@Tag(name = "Restaurant", description = "API for managing restaurants")
public class RestaurantController {

  private final RestaurantService restaurantService;

  @Operation(summary = "Create a new restaurant", description = "Adds a new restaurant to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurant created successfully", content = @Content(schema = @Schema(implementation = RestaurantDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<RestaurantDTO> createRestaurant(
      @RequestBody @Parameter(description = "Restaurant data to be created", required = true) @Valid RestaurantDTO restaurant) {
    return ResponseEntity.ok().body(restaurantService.insertRestaurant(restaurant));
  }

  @Operation(summary = "Get restaurant by ID", description = "Fetches a restaurant by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurant retrieved successfully", content = @Content(schema = @Schema(implementation = RestaurantDTO.class))),
      @ApiResponse(responseCode = "404", description = "Restaurant not found for the given ID")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/{id}")
  public ResponseEntity<RestaurantDTO> getRestaurantById(
      @PathVariable @Parameter(description = "Restaurant ID", required = true) Long id) {
    return ResponseEntity.ok().body(restaurantService.getDTOById(id));
  }

  @Operation(summary = "Search restaurants by name", description = "Finds restaurants that contain the specified name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantDTO.class)))),
      @ApiResponse(responseCode = "400", description = "Invalid query parameter")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/search-by-name")
  public ResponseEntity<List<RestaurantDTO>> getByNameContaining(
      @RequestParam @Parameter(description = "Name to search for", required = true) String name) {
    return ResponseEntity.ok().body(restaurantService.getByNameContaining(name));
  }

  @Operation(summary = "Get all restaurants", description = "Retrieves a list of all restaurants")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantDTO.class)))),
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("")
  public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
    return ResponseEntity.ok().body(restaurantService.getAll());
  }

  @Operation(summary = "Get restaurants by cuisine ID", description = "Fetches restaurants associated with a specific cuisine ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurants retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RestaurantDTO.class)))),
      @ApiResponse(responseCode = "404", description = "Cuisine not found for the given ID")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/by-cuisine/{cuisineId}")
  public ResponseEntity<List<RestaurantDTO>> getRestaurantsByCuisine(
      @PathVariable @Parameter(description = "Cuisine ID", required = true) Long cuisineId) {
    List<RestaurantDTO> restaurants = restaurantService.getRestaurantsByCuisineId(cuisineId);
    return ResponseEntity.ok(restaurants);
  }

  @Operation(summary = "Update restaurant visibility", description = "Updates the visibility status of a restaurant")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurant visibility updated successfully", content = @Content(schema = @Schema(implementation = UpdateResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("/visibility")
  public ResponseEntity<UpdateResponse> updateRestaurantVisibility(
      @RequestParam @Parameter(description = "Restaurant ID", required = true) Long id,
      @RequestParam @Parameter(description = "Visibility status", required = true) boolean visible) {
    restaurantService.updateVisibility(id, visible);
    return ResponseEntity.ok()
        .body(new UpdateResponse("Restaurant visibility has been changed successfully", id, visible));
  }

  @Operation(summary = "Delete a restaurant by ID", description = "Removes a restaurant from the system by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Restaurant deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Restaurant not found for the given ID")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRestaurant(
      @PathVariable @Parameter(description = "Restaurant ID", required = true) long id) {
    restaurantService.deleteRestaurant(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Update a restaurant", description = "Updates an existing restaurant's information")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Restaurant updated successfully", content = @Content(schema = @Schema(implementation = RestaurantDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("")
  public ResponseEntity<RestaurantDTO> updateRestaurant(
      @RequestBody @Parameter(description = "Updated restaurant data", required = true) RestaurantDTO updatedRestaurant) {
    return ResponseEntity.ok().body(restaurantService.updateRestaurant(updatedRestaurant));
  }
}
