package com.joe.abdelaziz.food_delivery_system.restaurants.cuisine;

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
@RequestMapping("/api/v1/cuisines")
@RequiredArgsConstructor
@Validated
@Tag(name = "Cuisine", description = "API for managing cuisines")
public class CuisineController {

  private final CuisineService cuisineService;

  @Operation(summary = "Add a new cuisine", description = "Adds a new cuisine to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cuisine added successfully", content = @Content(schema = @Schema(implementation = CuisineDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<CuisineDTO> addCuisine(
      @Valid @RequestBody @Parameter(description = "Cuisine data", required = true) CuisineDTO cuisine) {
    CuisineDTO newCuisine = cuisineService.insert(cuisine);
    return ResponseEntity.ok().body(newCuisine);
  }

  @Operation(summary = "Get a cuisine by ID", description = "Fetches details of a specific cuisine by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cuisine retrieved successfully", content = @Content(schema = @Schema(implementation = CuisineDTO.class))),
      @ApiResponse(responseCode = "403", description = "User is not authorized"),
      @ApiResponse(responseCode = "404", description = "Cuisine not found")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("/{id}")
  public ResponseEntity<CuisineDTO> getCuisineById(
      @PathVariable @Parameter(description = "Cuisine ID", required = true) Long id) {
    return ResponseEntity.ok().body(cuisineService.getById(id));
  }

  @Operation(summary = "Get all cuisines", description = "Retrieves a list of all cuisines")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cuisines retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CuisineDTO.class)))),
      @ApiResponse(responseCode = "403", description = "User is not authorized")
  })
  @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
  @GetMapping("")
  public ResponseEntity<List<CuisineDTO>> getAllCuisines() {
    return ResponseEntity.ok().body(cuisineService.getAll());
  }
}
