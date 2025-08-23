package com.example.SecureJwTeaTime.events.userregistration;

import com.example.SecureJwTeaTime.domain.user.base.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRegistrationPublisher {
  private final ApplicationEventPublisher publisher;

  public void publishCustomerRegistrationEvent(final User user, String activationCode) {
    UserRegistrationEvent userRegistrationEvent =
        new UserRegistrationEvent(
            this, user.getEmail(), user.getId(), user.getAccountName(), activationCode);

    publisher.publishEvent(userRegistrationEvent);
  }
}
