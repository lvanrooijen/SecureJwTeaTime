package com.example.SecureJwTeaTime.domain.user.base;

import com.example.SecureJwTeaTime.domain.user.company.CompanyAccount;
import com.example.SecureJwTeaTime.domain.user.customer.CustomerAccount;
import com.example.SecureJwTeaTime.util.routes.AppRoutes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  // TODO
  // login user

  // refresh token

  // logout user

  // get user by id

  // get all users
}
