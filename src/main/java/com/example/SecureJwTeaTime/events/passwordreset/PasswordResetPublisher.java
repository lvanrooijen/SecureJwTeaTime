package com.example.SecureJwTeaTime.events.passwordreset;

import com.example.SecureJwTeaTime.domain.user.base.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetPublisher {
  private final ApplicationEventPublisher publisher;

  public void publishPasswordResetEvent(User user, String resetLink) {
    PasswordResetEvent passwordResetEvent =
        new PasswordResetEvent(
            this, user.getEmail(), user.getId(), user.getAccountName(), resetLink);

    publisher.publishEvent(passwordResetEvent);
  }
}
