package com.example.SecureJwTeaTime.events.email;

import com.example.SecureJwTeaTime.domain.user.base.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailEventPublisher {
  private final ApplicationEventPublisher publisher;

  public void publishEmailEvent(User user, String subject, String template, String detail) {
    EmailEvent emailEvent =
        new EmailEvent(
            this, user.getEmail(), user.getId(), user.getAccountName(), subject, template, detail);

    publisher.publishEvent(emailEvent);
  }
}
