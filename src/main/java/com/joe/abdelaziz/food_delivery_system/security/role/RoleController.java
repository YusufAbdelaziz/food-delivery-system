package com.joe.abdelaziz.food_delivery_system.security.role;

import java.util.List;

import org.springframework.http.ResponseEntity;
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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "API for managing roles")
public class RoleController {

  private final RoleService roleService;

  @Operation(summary = "Create a new role", description = "Adds a new role to the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Role created successfully", content = @Content(schema = @Schema(implementation = Role.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PostMapping("")
  public ResponseEntity<Role> addRole(
      @RequestBody @Parameter(description = "Role creation data", required = true) Role role) {
    Role insertedRole = roleService.insertRole(role);
    return ResponseEntity.ok().body(insertedRole);
  }

  @Operation(summary = "Get all roles", description = "Retrieves a list of all roles")
  @ApiResponse(responseCode = "200", description = "List of roles retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Role.class))))
  @GetMapping("")
  public ResponseEntity<List<Role>> getAllRoles() {
    return ResponseEntity.ok().body(roleService.getAllRoles());
  }

  @Operation(summary = "Get role by ID", description = "Retrieves a specific role by its ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Role retrieved successfully", content = @Content(schema = @Schema(implementation = Role.class))),
      @ApiResponse(responseCode = "404", description = "Role not found")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Role> getRoleById(
      @PathVariable @Parameter(description = "ID of the role to be retrieved", required = true) Long id) {
    return ResponseEntity.ok().body(roleService.findRoleById(id).get());
  }

}
