package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.domain.user.base.dto.GetUserWithJwtToken;
import com.example.SecureJwTeaTime.domain.user.base.dto.LoginUser;
import com.example.SecureJwTeaTime.domain.user.company.CompanyAccount;
import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import com.example.SecureJwTeaTime.util.routes.AppRoutes;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Handles HTTP requests related to {@link User}
 *
 * <p>Does not include actions related to subclasses like {@link CustomerAccount} or {@link
 * CompanyAccount}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(AppRoutes.AUTH)
public class UserController {
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<GetUserWithJwtToken> login(
      @RequestBody LoginUser requestBody, HttpServletResponse response) {
    GetUserWithJwtToken user = userService.login(response, requestBody);

    return ResponseEntity.ok(user);
  }

  /**
   * Activates a user through provided code.
   *
   * <p>User must be authenticated to access this resource
   */
  @PostMapping("/activate")
  public ResponseEntity<Void> activate(
      @RequestParam(required = true, name = "activationCode") UUID code) {
    userService.activateUser(code);

    return ResponseEntity.noContent().build();
  }

  // refresh token

  // logout user

  // get user by id

  // get all users
}
