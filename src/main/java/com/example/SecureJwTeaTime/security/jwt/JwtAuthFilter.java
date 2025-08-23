package com.example.SecureJwTeaTime.security.jwt;

import static com.example.SecureJwTeaTime.util.constants.JwtConstants.AUTHORIZATION_HEADER_JWT_PREFIX;
import static com.example.SecureJwTeaTime.util.constants.JwtConstants.AUTHORIZATION_HEADER_NAME;

import com.example.SecureJwTeaTime.domain.user.base.User;
import com.example.SecureJwTeaTime.domain.user.base.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
  private final UserService userService;
  private final JwtService jwtService;

  private static boolean requestHasValidAuthHeader(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
    return authHeader != null && authHeader.startsWith(AUTHORIZATION_HEADER_JWT_PREFIX);
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      filterChain.doFilter(request, response);
      return;
    }

    if (requestHasValidAuthHeader(request)) {
      getUserFromAuthorizationHeader(request.getHeader(AUTHORIZATION_HEADER_NAME))
          .ifPresent(
              principal -> {
                UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
              });
    }

    filterChain.doFilter(request, response);
  }

  private Optional<User> getUserFromAuthorizationHeader(String authorization) {
    return jwtService
        .readToken(authorization.substring(AUTHORIZATION_HEADER_JWT_PREFIX.length()))
        .filter(token -> !token.isExpired())
        .flatMap(
            token -> Optional.ofNullable((User) userService.loadUserByUsername(token.email())));
  }
}
