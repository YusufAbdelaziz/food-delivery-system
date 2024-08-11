package com.joe.abdelaziz.food_delivery_system.restaurants.specOption;

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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/spec-options")
@AllArgsConstructor
@Validated
@Slf4j
@Tag(name = "Spec Options", description = "API for managing spec options")
public class SpecOptionController {

  private final SpecOptionService specOptionService;
  private final ObjectMapper objectMapper;

  @Operation(summary = "Create a new spec option", description = "Creates a new spec option for a given spec")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Spec option created successfully", content = @Content(schema = @Schema(implementation = SpecOptionDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<SpecOptionDTO> createSpecOption(
      @RequestBody @Parameter(description = "Spec option creation data including spec ID", required = true) @Valid JsonNode node) {
    if (!node.has("specId") || !node.has("option")) {
      throw new BusinessLogicException("You should provide a specId and option to insert a new spec option");
    }

    try {
      SpecOptionDTO specOption = objectMapper.treeToValue(node.get("option"), SpecOptionDTO.class);
      Long specId = node.get("specId").asLong();
      return ResponseEntity.status(HttpStatus.CREATED).body(specOptionService.insertSpecOption(specOption, specId));
    } catch (JsonProcessingException | IllegalArgumentException e) {
      log.error("Could not parse specId and option fields", e);
      throw new BusinessLogicException("Could not parse specId and option fields");
    }
  }

  @Operation(summary = "Get a spec option by ID", description = "Retrieves a spec option by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Spec option retrieved successfully", content = @Content(schema = @Schema(implementation = SpecOption.class))),
      @ApiResponse(responseCode = "404", description = "Spec option not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<SpecOption> getSpecOptionById(
      @PathVariable @Parameter(description = "ID of the spec option to be retrieved", required = true) Long id) {
    return ResponseEntity.ok().body(specOptionService.getById(id));
  }

  @Operation(summary = "Update a spec option", description = "Updates an existing spec option")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Spec option updated successfully", content = @Content(schema = @Schema(implementation = SpecOption.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("")
  public ResponseEntity<SpecOption> updateSpecOption(
      @RequestBody @Parameter(description = "Updated spec option data", required = true) SpecOption updatedOption) {
    return ResponseEntity.ok().body(specOptionService.updateSpecOption(updatedOption));
  }

  @Operation(summary = "Delete a spec option", description = "Deletes a spec option by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Spec option deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Spec option not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOption(
      @PathVariable @Parameter(description = "ID of the spec option to be deleted", required = true) long id) {
    specOptionService.deleteItem(id);
    return ResponseEntity.noContent().build();
  }

}
