package com.example.SecureJwTeaTime.domain.user.customer;

import com.example.SecureJwTeaTime.domain.user.customer.dto.GetCustomer;
import com.example.SecureJwTeaTime.domain.user.customer.dto.PostCustomer;
import com.example.SecureJwTeaTime.util.routes.AppRoutes;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppRoutes.CUSTOMERS)
public class CustomerController {
  private final CustomerService customerService;

  @PostMapping("/register")
  public ResponseEntity<GetCustomer> register(
      @Valid @RequestBody PostCustomer postCustomer, HttpServletResponse response) {
    GetCustomer customer = customerService.register(postCustomer, response);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(customer.id())
            .toUri();

    return ResponseEntity.created(location).body(customer);
  }
  // patch

  // delete
}
