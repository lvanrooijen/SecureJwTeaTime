package com.example.SecureJwTeaTime.events.email;

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
public class EmailEventListener {
  private final EmailService emailService;

  @EventListener
  @Async
  public void handleEmailEvent(EmailEvent emailEvent) {
    try {
      emailService.sendMailTemplate(
          emailEvent.getEmail(),
          emailEvent.getName(),
          emailEvent.getSubject(),
          emailEvent.getTemplate(),
          emailEvent.getDetail());
    } catch (MessagingException e) {
      log.warn(String.format("[MESSAGING EXCEPTION] detail: %s", e.getMessage()));
    }
  }
}
