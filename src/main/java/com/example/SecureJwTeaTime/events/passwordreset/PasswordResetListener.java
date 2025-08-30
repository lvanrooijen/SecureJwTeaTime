package com.example.SecureJwTeaTime.events.passwordreset;

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
public class PasswordResetListener {
  private final EmailService emailService;

  @EventListener
  @Async
  public void handlePasswordResetEvent(PasswordResetEvent event) {
    log.info(
        String.format(
            "[USER PASSWORD RESET REQUESTED] email=%s userID=%s",
            event.getEmail(), event.getUserId()));
    try {
      emailService.sendMailTemplate(
          event.getEmail(),
          event.getName(),
          "Password Reset",
          "PasswordResetRequest.html",
          event.getActivationLink());
    } catch (MessagingException e) {
      log.warn(String.format("[MESSAGING EXCEPTION] detail: %s", e.getMessage()));
      // TODO even nadenken wat hier te doen.
    }
  }
}
