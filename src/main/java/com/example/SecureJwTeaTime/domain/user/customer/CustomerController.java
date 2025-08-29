package com.example.SecureJwTeaTime.domain.user.customer;

import com.example.SecureJwTeaTime.domain.user.base.dto.GetUserWithJwtToken;
import com.example.SecureJwTeaTime.domain.user.customer.dto.GetCustomer;
import com.example.SecureJwTeaTime.domain.user.customer.dto.PatchCustomer;
import com.example.SecureJwTeaTime.domain.user.customer.dto.PostCustomer;
import com.example.SecureJwTeaTime.util.routes.AppRoutes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppRoutes.CUSTOMERS)
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping("/register")
  public ResponseEntity<GetUserWithJwtToken> register(
      @Valid @RequestBody PostCustomer postCustomer, HttpServletResponse response) {
    GetUserWithJwtToken customer = customerService.register(postCustomer, response);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(customer.id())
            .toUri();

    return ResponseEntity.created(location).body(customer);
  }

  // patch
  @PatchMapping("/{id}")
  public ResponseEntity<GetCustomer> patch(
      @PathVariable UUID id, @Valid @RequestBody PatchCustomer patch) {
    GetCustomer customer = customerService.update(id, patch);

    return ResponseEntity.ok(customer);
  }

  // delete
}
