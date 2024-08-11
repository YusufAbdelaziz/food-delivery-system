package com.joe.abdelaziz.food_delivery_system.security.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.joe.abdelaziz.food_delivery_system.admin.Admin;
import com.joe.abdelaziz.food_delivery_system.admin.AdminService;
import com.joe.abdelaziz.food_delivery_system.base.AppUser;
import com.joe.abdelaziz.food_delivery_system.courier.Courier;
import com.joe.abdelaziz.food_delivery_system.courier.CourierService;
import com.joe.abdelaziz.food_delivery_system.customer.Customer;
import com.joe.abdelaziz.food_delivery_system.customer.CustomerService;
import com.joe.abdelaziz.food_delivery_system.security.config.JwtService;
import com.joe.abdelaziz.food_delivery_system.security.role.RoleType;
import com.joe.abdelaziz.food_delivery_system.security.token.Token;
import com.joe.abdelaziz.food_delivery_system.security.token.TokenRepository;
import com.joe.abdelaziz.food_delivery_system.security.token.TokenType;
import com.joe.abdelaziz.food_delivery_system.utiles.exception.BusinessLogicException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final CourierService courierService;
  private final AdminService adminService;
  private final CustomerService customerService;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    if (request.getRole() == null || request.getRole().getType() == null) {
      throw new BusinessLogicException("You should provide a valid role (USER, ADMIN, COURIER)");
    }

    UserDetails user = createUser(request);
    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", user.getAuthorities());
    String jwtToken = jwtService.generateToken(claims, user);
    String refreshToken = jwtService.generateRefreshToken(claims, user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    // Remember, the username is a combination of the role + phone number.
    // which is used in [CustomUserDetailsService] class
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getRole().getType().toString() + ":" + request.getPhoneNumber(),
            request.getPassword()));

    UserDetails user = findUserByPhoneNumber(request.getPhoneNumber(), request.getRole().getType());
    if (user == null) {
      throw new BusinessLogicException("No user found with this phone number");
    }

    Map<String, Object> claims = new HashMap<>();
    claims.put("authorities", user.getAuthorities());
    String jwtToken = jwtService.generateToken(claims, user);
    String refreshToken = jwtService.generateRefreshToken(claims, user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse refreshToken(HttpServletRequest request) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new BusinessLogicException("Invalid or missing Authorization header");
    }

    final String refreshToken = authHeader.substring(7);
    final String userPhoneNumber = jwtService.extractUserPhoneNumberAndRole(refreshToken).split(":")[1];
    final RoleType roleType = RoleType.valueOf(jwtService.extractUserPhoneNumberAndRole(refreshToken).split(":")[0]);
    if (userPhoneNumber == null) {
      throw new BusinessLogicException("Invalid refresh token");
    }

    UserDetails user = findUserByPhoneNumber(userPhoneNumber, roleType);

    if (user == null) {
      throw new BusinessLogicException("User not found");
    }

    if (jwtService.isTokenValid(refreshToken, user)) {
      Map<String, Object> claims = new HashMap<>();
      claims.put("authorities", user.getAuthorities());
      String accessToken = jwtService.generateToken(claims, user);
      String newRefreshToken = jwtService.generateRefreshToken(claims, user);

      revokeAllUserTokens(user);
      saveUserToken(user, accessToken);

      return AuthenticationResponse.builder()
          .accessToken(accessToken)
          .refreshToken(newRefreshToken)
          .build();
    } else {
      throw new BusinessLogicException("Invalid refresh token");
    }
  }

  private UserDetails createUser(RegisterRequest request) {
    UserDetails user;
    switch (request.getRole().getType()) {
      case COURIER:
        user = createCourier(request);
        break;
      case ADMIN:
        user = createAdmin(request);
        break;
      case USER:
        user = createCustomer(request);
        break;
      default:
        throw new BusinessLogicException("Invalid role type");
    }
    return user;
  }

  private Courier createCourier(RegisterRequest request) {
    Courier courier = new Courier();
    courier.setName(request.getName());
    courier.setPhoneNumber(request.getPhoneNumber());
    courier.setPassword(passwordEncoder.encode(request.getPassword()));
    courier.setRole(request.getRole());
    return courierService.insertCourier(courier);
  }

  private Admin createAdmin(RegisterRequest request) {
    Admin admin = new Admin();
    admin.setName(request.getName());
    admin.setPassword(passwordEncoder.encode(request.getPassword()));
    admin.setPhoneNumber(request.getPhoneNumber());
    admin.setRole(request.getRole());
    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      admin.setEmail(request.getEmail());
    }
    return adminService.insertAdmin(admin);
  }

  private Customer createCustomer(RegisterRequest request) {
    Customer customer = new Customer();
    customer.setName(request.getName());
    customer.setPassword(passwordEncoder.encode(request.getPassword()));
    customer.setPhoneNumber(request.getPhoneNumber());
    customer.setRole(request.getRole());
    if (request.getEmail() != null && !request.getEmail().isBlank()) {
      customer.setEmail(request.getEmail());
    }
    return customerService.insertUser(customer);
  }

  private UserDetails findUserByPhoneNumber(String phoneNumber, RoleType roleType) {
    UserDetails user = null;
    if (roleType == RoleType.ADMIN) {
      user = adminService.findByPhoneNumber(phoneNumber).orElse(null);
    } else if (roleType == RoleType.COURIER) {
      user = courierService.findByPhoneNumber(phoneNumber).orElse(null);
    } else if (roleType == RoleType.USER) {
      user = customerService.findByPhoneNumber(phoneNumber).orElse(null);
    }

    return user;
  }

  private void revokeAllUserTokens(UserDetails user) {
    List<Token> validUserTokens = new ArrayList<>();

    if (user instanceof AppUser) {
      validUserTokens = tokenRepository.findAllValidTokenByUser(getUserId(user));
    } else if (user instanceof Courier) {
      validUserTokens = tokenRepository.findAllValidTokenByCourier(getUserId(user));
    }
    if (!validUserTokens.isEmpty()) {
      validUserTokens.forEach(token -> {
        token.setExpired(true);
        token.setRevoked(true);
      });
      tokenRepository.saveAll(validUserTokens);
    }
  }

  private void saveUserToken(UserDetails user, String jwtToken) {
    Token token = Token.builder()
        .user(user instanceof AppUser ? (AppUser) user : null)
        .courier(user instanceof Courier ? (Courier) user : null)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private Long getUserId(UserDetails user) {
    if (user instanceof AppUser) {
      return ((AppUser) user).getId();
    } else if (user instanceof Courier) {
      return ((Courier) user).getId();
    }
    throw new BusinessLogicException("Invalid user type");
  }
}