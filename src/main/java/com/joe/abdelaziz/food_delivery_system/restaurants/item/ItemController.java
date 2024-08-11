package com.joe.abdelaziz.food_delivery_system.restaurants.item;

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
import com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec.SpecDTO;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Item", description = "API for managing items")
public class ItemController {

  private final ItemService itemService;
  private final ObjectMapper objectMapper;

  @Operation(summary = "Create a new item", description = "Adds a new item to the specified section")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Item created successfully", content = @Content(schema = @Schema(implementation = ItemDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<ItemDTO> createItem(
      @RequestBody @Parameter(description = "Item data with section ID", required = true) JsonNode node) {
    if (!node.has("sectionId") || !node.has("item")) {
      throw new BusinessLogicException("You should provide a section and a menu id to insert a new section");
    }

    try {
      ItemDTO itemDto = objectMapper.treeToValue(node.get("item"), ItemDTO.class);
      Long sectionId = node.get("sectionId").asLong();
      return ResponseEntity.status(HttpStatus.CREATED).body(itemService.insertItem(itemDto, sectionId));
    } catch (JsonProcessingException | IllegalArgumentException e) {
      log.error("Could not parse sectionId and item fields", e);
      throw new BusinessLogicException("Could not parse section and menuId fields");
    }
  }

  @Operation(summary = "Create specs for an item", description = "Adds specs to an existing item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Specs created successfully", content = @Content(schema = @Schema(implementation = ItemDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{itemId}/specs")
  public ResponseEntity<ItemDTO> createSpecs(
      @PathVariable @Parameter(description = "Item ID", required = true) Long itemId,
      @RequestBody @Parameter(description = "List of specs to be added", required = true) List<SpecDTO> specs) {
    ItemDTO item = itemService.insertSpecs(itemId, specs);
    return ResponseEntity.status(HttpStatus.CREATED).body(item);
  }

  @Operation(summary = "Get item by ID", description = "Fetches details of a specific item by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item retrieved successfully", content = @Content(schema = @Schema(implementation = ItemDTO.class))),
      @ApiResponse(responseCode = "404", description = "Item not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<ItemDTO> getItemById(
      @PathVariable @Parameter(description = "Item ID", required = true) Long id) {
    return ResponseEntity.ok().body(itemService.getDTOById(id));
  }

  @Operation(summary = "Update an existing item", description = "Updates the details of an existing item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Item updated successfully", content = @Content(schema = @Schema(implementation = ItemDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("")
  public ResponseEntity<ItemDTO> updateItem(
      @RequestBody @Parameter(description = "Item data to be updated", required = true) ItemDTO updateItem) {
    return ResponseEntity.ok().body(itemService.updateItem(updateItem));
  }

  @Operation(summary = "Delete an item", description = "Removes an item from the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Item deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Item not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteItem(
      @PathVariable @Parameter(description = "Item ID", required = true) long id) {
    itemService.deleteItem(id);
    return ResponseEntity.noContent().build();
  }

}
