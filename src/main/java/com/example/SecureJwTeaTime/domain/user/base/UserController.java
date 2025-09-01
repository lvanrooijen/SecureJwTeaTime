package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.domain.user.base.dto.*;
import com.example.SecureJwTeaTime.util.routes.AppRoutes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/** Handles HTTP requests related to {@link User} */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppRoutes.AUTH)
public class UserController {
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<GetUserWithJwtToken> login(
      @Valid @RequestBody LoginUser requestBody, HttpServletResponse response) {
    GetUserWithJwtToken user = userService.login(response, requestBody);

    return ResponseEntity.ok(user);
  }

  @PostMapping("/activate")
  public ResponseEntity<Void> activate(
      @RequestParam(required = true, name = "activationCode") UUID code) {
    userService.activateUser(code);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<GetUserWithJwtToken> refreshToken(
      HttpServletRequest request, HttpServletResponse response) {
    GetUserWithJwtToken user = userService.refreshToken(request, response);

    return ResponseEntity.ok(user);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    userService.logout(request);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/request-password-reset")
  public ResponseEntity<Void> requestPasswordReset(@Valid @RequestBody PasswordResetRequest body) {
    userService.requestPasswordReset(body);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/reset-password/{activationCode}")
  public ResponseEntity<GetUserWithJwtToken> resetPassword(
      @Valid @RequestBody NewPasswordRequest body, @PathVariable UUID activationCode) {
    userService.resetPassword(activationCode, body);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("users/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<GetUser> getById(@PathVariable UUID id) {
    GetUser user = userService.getById(id);

    return ResponseEntity.ok(user);
  }

  @GetMapping("users")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<GetUser>> getAll() {
    List<GetUser> users = userService.getAll();

    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/users/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    userService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
