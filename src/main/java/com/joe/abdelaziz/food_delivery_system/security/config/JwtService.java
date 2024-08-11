package com.joe.abdelaziz.food_delivery_system.security.config;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${application.security.jwt.secret-key}")
  private String SECRET_KEY;
  @Value("${application.security.jwt.expiration}")
  private long JWT_EXPIRATION_DURATION;
  @Value("${application.security.jwt.refresh-token.expiration}")
  private long REFRESH_EXPIRATION_DURATION;

  public String extractUserPhoneNumberAndRole(String token) {
    return extractClaim(token, new Function<Claims, String>() {
      @Override
      public String apply(Claims claim) {
        @SuppressWarnings({ "unchecked" })
        List<LinkedHashMap<String, String>> authorities = (List<LinkedHashMap<String, String>>) claim
            .get("authorities");

        // append role to the phone number like USER:01004895720.
        // Note that the role has ROLE_ appended to it so it needs to be removed (e.g
        // ROLE_USER).
        String rolePrefix = "ROLE_";
        return authorities.getLast().get("authority").substring(rolePrefix.length()) + ":" + claim.getSubject();
      }
    });
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, JWT_EXPIRATION_DURATION);
  }

  public String generateRefreshToken(
      UserDetails userDetails) {
    return buildToken(new HashMap<>(), userDetails, REFRESH_EXPIRATION_DURATION);
  }

  public String generateRefreshToken(
      Map<String, Object> extraClaims,
      UserDetails userDetails) {
    return buildToken(extraClaims, userDetails, REFRESH_EXPIRATION_DURATION);
  }

  private String buildToken(Map<String, Object> extraClaims, UserDetails userDetail, Long duration) {
    return Jwts
        .builder()
        .claims(extraClaims)
        .subject(userDetail.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + duration))
        .signWith(getSignInKey(), Jwts.SIG.HS256).compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String phoneNumber = extractUserPhoneNumberAndRole(token).split(":")[1];
    return (phoneNumber.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}