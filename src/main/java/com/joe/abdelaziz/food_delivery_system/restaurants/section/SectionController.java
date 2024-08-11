package com.joe.abdelaziz.food_delivery_system.restaurants.section;

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
@RequestMapping("/api/v1/sections")
@AllArgsConstructor
@Validated
@Slf4j
@Tag(name = "Sections", description = "API for managing sections in menus")
public class SectionController {

  private final SectionService sectionService;
  private final ObjectMapper objectMapper;

  @Operation(summary = "Create a new section", description = "Creates a new section within a menu")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Section created successfully", content = @Content(schema = @Schema(implementation = SectionDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("")
  public ResponseEntity<SectionDTO> createSection(
      @RequestBody @Parameter(description = "Section creation data including menu ID", required = true) @Valid JsonNode node) {
    if (!node.has("menuId") || !node.has("section")) {
      throw new BusinessLogicException("You should provide a section and a menu id to insert a new section");
    }

    try {
      SectionDTO section = objectMapper.treeToValue(node.get("section"), SectionDTO.class);
      Long menuId = node.get("menuId").asLong();
      return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.insertSection(section, menuId));
    } catch (JsonProcessingException | IllegalArgumentException e) {
      log.error("Could not parse section and menuId fields", e);
      throw new BusinessLogicException("Could not parse section and menuId fields");
    }
  }

  @Operation(summary = "Get a section by ID", description = "Retrieves a section by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Section retrieved successfully", content = @Content(schema = @Schema(implementation = SectionDTO.class))),
      @ApiResponse(responseCode = "404", description = "Section not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<SectionDTO> getSectionById(
      @PathVariable @Parameter(description = "ID of the section to be retrieved", required = true) Long id) {
    return ResponseEntity.ok().body(sectionService.getDTOById(id));
  }

  @Operation(summary = "Update a section", description = "Updates an existing section")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Section updated successfully", content = @Content(schema = @Schema(implementation = SectionDTO.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("")
  public ResponseEntity<SectionDTO> updateSection(
      @RequestBody @Parameter(description = "Updated section data", required = true) SectionDTO updatedSection) {
    return ResponseEntity.ok().body(sectionService.updateSection(updatedSection));
  }

  @Operation(summary = "Delete a section", description = "Deletes a section by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Section deleted successfully"),
      @ApiResponse(responseCode = "404", description = "Section not found")
  })
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSection(
      @PathVariable @Parameter(description = "ID of the section to be deleted", required = true) long id) {
    sectionService.deleteSection(id);
    return ResponseEntity.noContent().build();
  }
}
