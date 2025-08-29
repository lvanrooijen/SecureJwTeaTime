package com.example.SecureJwTeaTime.security.refreshtoken;

import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final JwtService jwtService;
  private final RefreshTokenRepository refreshTokenRepository;

  /**
   * Creates or updates a {@link RefreshToken} for the user, saves it in the database, and stores it
   * in an HTTP-only cookie.
   *
   * <p>details:
   *
   * <ul>
   *   <li>If no refresh token exists for the user, a new one is created
   *   <li>If the existing token is expired, a new token value is generated and the expiration date
   *       is reset
   *   <li>The resulting refresh token is saved in the database
   *   <li>The token is stored in an HTTP-only cookie, secure cookie with path "/api/v1/auth" and
   *       SameSite=Strict
   * </ul>
   *
   * @param response the {@link HttpServletResponse} to which the cookie will be added
   * @param user {@link User}
   * @return created/updated {@link RefreshToken}
   */
  @Transactional
  public RefreshToken processRefreshToken(HttpServletResponse response, User user) {
    RefreshToken refreshToken = getOrCreateAndSave(user);
    addToCookie(response, refreshToken.getToken());
    return refreshToken;
  }

  /**
   * Gets of creates a {@link RefreshToken} and saves it in the database
   *
   * <p>if existing Refresh Token is expired it sets a new token value and a new expiration date.
   *
   * @param user
   * @return updated/created {@link RefreshToken}
   */
  @Transactional
  public RefreshToken getOrCreateAndSave(User user) {
    RefreshToken refreshToken =
        refreshTokenRepository
            .findByUser(user)
            .orElse(
                RefreshToken.builder()
                    .user(user)
                    .token(jwtService.generateRefreshToken(user))
                    .isRevoked(false)
                    .build());

    if (isExpired(refreshToken)) {
      refreshToken.setToken(jwtService.generateRefreshToken(user));
      refreshToken.setExpiresAt(LocalDate.now().plusDays(7));
    }

    return refreshTokenRepository.save(refreshToken);
  }

  /**
   * Sets the HTTP cookie.
   *
   * <p>The token is stored in an HTTP-only cookie, secure the cookie with path "/api/v1/auth" and
   * SameSite=Strict
   *
   * @param response {@link HttpServletResponse}
   * @param refreshToken {@link RefreshToken}
   */
  public void addToCookie(HttpServletResponse response, String refreshToken) {
    ResponseCookie cookie =
        ResponseCookie.from("refreshToken", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/api/v1/auth")
            .maxAge(Duration.ofDays(7))
            .sameSite("Strict")
            .build();

    response.addHeader("Set-Cookie", cookie.toString());
  }

  public Boolean isExpired(RefreshToken refreshToken) {
    return refreshToken.getExpiresAt().isBefore(LocalDate.now());
  }
}
