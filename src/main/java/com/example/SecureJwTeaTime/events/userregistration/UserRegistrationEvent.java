package com.example.SecureJwTeaTime.events.userregistration;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
  private final String email;
  private final UUID userId;
  private final String name;
  private final String activationCode;

  public UserRegistrationEvent(
      Object source, String email, UUID userId, String name, String activationCode) {
    super(source);
    this.email = email;
    this.userId = userId;
    this.name = name;
    this.activationCode = activationCode;
  }
}
