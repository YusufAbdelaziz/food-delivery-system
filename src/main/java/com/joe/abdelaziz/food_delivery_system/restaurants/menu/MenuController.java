package com.joe.abdelaziz.food_delivery_system.restaurants.menu;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
@Validated
@Tag(name = "Menu", description = "API for managing menus")
public class MenuController {

  private final MenuService menuService;

  @Operation(summary = "Create a new menu", description = "Adds a new menu for a restaurant")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Menu created successfully", content = @Content(schema = @Schema(implementation = MenuDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<MenuDTO> createMenu(
      @RequestBody @Parameter(description = "Menu data to be created", required = true) @Valid MenuDTO menu) {
    return ResponseEntity.status(HttpStatus.CREATED).body(menuService.insertMenu(menu));
  }

  @Operation(summary = "Retrieve a menu by restaurant ID", description = "Fetches the menu associated with a specific restaurant ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Menu retrieved successfully", content = @Content(schema = @Schema(implementation = MenuDTO.class))),
      @ApiResponse(responseCode = "404", description = "Menu not found for the given restaurant ID")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/restaurants/{restaurantId}")
  public ResponseEntity<MenuDTO> getMenuByRestaurantId(
      @PathVariable @Parameter(description = "Restaurant ID", required = true) Long restaurantId) {
    MenuDTO menu = menuService.getByRestaurantId(restaurantId);
    return ResponseEntity.ok().body(menu);
  }

  @Operation(summary = "Get all menus", description = "Retrieves a list of all menus")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Menus retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MenuDTO.class)))),
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("")
  public ResponseEntity<List<MenuDTO>> getAllMenus() {
    return ResponseEntity.ok().body(menuService.getAll());
  }

  @Operation(summary = "Delete a menu by ID", description = "Removes a menu from the system by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Menu deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Menu not found for the given ID")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMenuById(
      @PathVariable @Parameter(description = "Menu ID", required = true) long id) {
    menuService.deleteMenu(id);
    return ResponseEntity.noContent().build();
  }

}
