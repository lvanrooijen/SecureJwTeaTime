package com.example.SecureJwTeaTime.security.jwt;

import static com.example.SecureJwTeaTime.util.constants.JwtConstants.*;

import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.security.jwt.dto.JwtTokenDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/** Jwt service is responsible for generating and validating the token */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class JwtService {
  private final SecretKey jwtSecretKey;

  /**
   * generates a jwt token for the user
   *
   * @param user who the token is generated for
   * @return jwt token
   */
  public String generateAccessToken(User user) {
    return buildToken(user, JWT_EXPIRATION_MS);
  }

  /**
   * generates a refresh token for the user
   *
   * @param user who the token is generated for
   * @return jwt token
   */
  public String generateRefreshToken(User user) {
    return buildToken(user, REFRESH_TOKEN_EXPIRATION_MS);
  }

  /**
   * Builds a jwt-token
   *
   * @param user who the token is generated for
   * @param expiration_time represents how long it takes for the token to expire
   * @return jwt token
   */
  public String buildToken(User user, long expiration_time) {
    long currentTimeMillis = System.currentTimeMillis();
    Map<String, Object> claims = new HashMap<>();
    claims.put(ROLES_CLAIM_NAME, convertAuthoritiesToRoles(user));

    return Jwts.builder()
        .subject(user.getEmail())
        .issuedAt(new Date(currentTimeMillis))
        .expiration(new Date(currentTimeMillis + expiration_time))
        .claims(claims)
        .signWith(jwtSecretKey)
        .compact();
  }

  /**
   * Converts users granted authorities to a list of roles in string format
   *
   * @param user user
   * @return list of user roles as a string format
   */
  public List<String> convertAuthoritiesToRoles(User user) {
    return user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList());
  }

  public Optional<JwtTokenDetails> readToken(String token) {
    try {
      Claims claims =
          Jwts.parser().verifyWith(jwtSecretKey).build().parseSignedClaims(token).getPayload();
      return Optional.of(
          new JwtTokenDetails(
              claims.getSubject(),
              getRolesFromClaims(claims),
              claims.getIssuedAt(),
              claims.getExpiration()));
    } catch (RuntimeException ex) {

      log.warn("[" + ex.getClass().getSimpleName() + "]" + " " + ex.getMessage());

      return Optional.empty();
    }
  }

  public boolean isTokenExpired(String token) {
    JwtTokenDetails tokenDetails = readToken(token).orElse(null);
    if (tokenDetails == null) {
      return true;
    } else {
      return tokenDetails.expiresAt().before(new Date());
    }
  }

  private String[] getRolesFromClaims(Claims claims) {
    Object rolesObject = claims.get(ROLES_CLAIM_NAME);

    if (rolesObject == null) {
      throw new IllegalArgumentException(ROLES_CLAIM_NAME + " claim not found");
    }

    if (!(rolesObject instanceof Iterable<?> rawRoles)) {
      throw new IllegalArgumentException("claims " + ROLES_CLAIM_NAME + " value is invalid");
    }

    List<String> parsedRoles = new ArrayList<>();

    for (Object o : rawRoles) {
      if (o instanceof String parsedRole) {
        parsedRoles.add(parsedRole);
      } else {
        log.warn(String.format("role is not a valid type: %s", o.getClass().getSimpleName()));
      }
    }

    return parsedRoles.toArray(new String[0]);
  }
}
