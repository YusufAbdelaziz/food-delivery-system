package com.joe.abdelaziz.food_delivery_system.restaurants.itemSpec;

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
import com.joe.abdelaziz.food_delivery_system.restaurants.specOption.SpecOptionDTO;
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
@RequestMapping("/api/v1/specs")
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Spec", description = "API for managing specifications")
public class SpecController {

  private final SpecService specService;
  private final ObjectMapper objectMapper;

  @Operation(summary = "Create a new specification", description = "Adds a new specification to the specified item")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Specification created successfully", content = @Content(schema = @Schema(implementation = SpecDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<SpecDTO> createSpec(
      @RequestBody @Parameter(description = "Specification data with item ID", required = true) JsonNode node) {
    if (!node.has("itemId") || !node.has("spec")) {
      throw new BusinessLogicException("You should provide a section and a menu id to insert a new section");
    }

    try {
      SpecDTO dto = objectMapper.treeToValue(node.get("spec"), SpecDTO.class);
      Long itemId = node.get("itemId").asLong();
      return ResponseEntity.status(HttpStatus.CREATED).body(specService.insertSpec(dto, itemId));
    } catch (JsonProcessingException | IllegalArgumentException e) {
      log.error("Could not parse specId and option fields", e);
      throw new BusinessLogicException("Could not parse section and menuId fields");
    }
  }

  @Operation(summary = "Add options to a specification", description = "Inserts options into an existing specification")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Specification options added successfully", content = @Content(schema = @Schema(implementation = SpecDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("/{specId}/spec-options")
  public ResponseEntity<SpecDTO> insertSpecs(
      @PathVariable @Parameter(description = "Specification ID", required = true) Long specId,
      @RequestBody @Parameter(description = "List of specification options", required = true) List<SpecOptionDTO> options) {
    SpecDTO spec = specService.insertSpecOptions(specId, options);
    return ResponseEntity.status(HttpStatus.CREATED).body(spec);
  }

  @Operation(summary = "Get specification by ID", description = "Fetches details of a specific specification by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Specification retrieved successfully", content = @Content(schema = @Schema(implementation = SpecDTO.class))),
      @ApiResponse(responseCode = "404", description = "Specification not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<SpecDTO> getSpecById(
      @PathVariable @Parameter(description = "Specification ID", required = true) Long id) {
    return ResponseEntity.ok().body(specService.getDTOById(id));
  }

  @Operation(summary = "Update an existing specification", description = "Updates the details of an existing specification")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Specification updated successfully", content = @Content(schema = @Schema(implementation = SpecDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("")
  public ResponseEntity<SpecDTO> updateSpec(
      @RequestBody @Parameter(description = "Specification data to be updated", required = true) SpecDTO updateSpec) {
    return ResponseEntity.ok().body(specService.updateSpec(updateSpec));
  }

  @Operation(summary = "Delete a specification", description = "Removes a specification from the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Specification deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Specification not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSpec(
      @PathVariable @Parameter(description = "Specification ID", required = true) long id) {
    specService.deleteSpec(id);
    return ResponseEntity.noContent().build();
  }

}
