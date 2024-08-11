package com.joe.abdelaziz.food_delivery_system.security.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Authentication", description = "API for user authentication and registration")
public class AuthenticationController {

  private final AuthenticationService service;

  @Operation(summary = "Register a new user", description = "Registers a new user in the system")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input data")
  })
  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody @Valid @Parameter(description = "User registration details", required = true) RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }

  @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns an authentication token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
  })
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody @Parameter(description = "User authentication details", required = true) AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @Operation(summary = "Refresh authentication token", description = "Refreshes the authentication token")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Token refreshed successfully", content = @Content(schema = @Schema(implementation = AuthenticationResponse.class))),
      @ApiResponse(responseCode = "400", description = "Invalid refresh token")
  })
  @PostMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponse> refreshToken(
      @Parameter(description = "HTTP request containing the refresh token", required = true) HttpServletRequest request,
      @Parameter(description = "HTTP response to handle the token refresh", required = true) HttpServletResponse response)
      throws IOException {
    return ResponseEntity.ok(service.refreshToken(request));
  }
}
