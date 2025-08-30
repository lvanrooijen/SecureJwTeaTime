package com.example.SecureJwTeaTime.events.passwordreset;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PasswordResetEvent extends ApplicationEvent {
  private final String email;
  private final UUID userId;
  private final String name;
  private final String activationLink;

  public PasswordResetEvent(
      Object source, String email, UUID userId, String name, String activationLink) {
    super(source);
    this.email = email;
    this.userId = userId;
    this.name = name;
    this.activationLink = activationLink;
  }
}
