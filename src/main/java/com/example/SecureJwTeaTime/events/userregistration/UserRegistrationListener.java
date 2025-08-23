package com.example.SecureJwTeaTime.events.userregistration;

import com.example.SecureJwTeaTime.email.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserRegistrationListener {
  private final EmailService emailService;

  @EventListener
  @Async
  public void handleUserRegistrationEvent(UserRegistrationEvent event) {
    log.info(
        String.format(
            "[USER REGISTRATION EVENT] [email=%s] [userID=%s]",
            event.getEmail(), event.getUserId()));
    try {
      emailService.sendUserRegistrationEmail(
          event.getEmail(), event.getName(), event.getActivationCode());
    } catch (MessagingException e) {
      log.warn(String.format("[MESSAGING EXCEPTION] detail: %s", e.getMessage()));
    }
  }
}
