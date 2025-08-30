package com.example.SecureJwTeaTime.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/** This class handles sending emails */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {
  private final JavaMailSender mailSender;
  private final TemplateEngine templateEngine;

  /**
   * basic method to send an email
   *
   * @param to email address of the recipient
   * @param subject subject of the email
   * @param text body of the email
   */
  public void sendMail(String to, String fullName, String subject, String text)
      throws MessagingException {
    Context context = new Context();
    context.setVariable("fullName", fullName);
    context.setVariable("text", text);
    context.setVariable("year", LocalDate.now().getYear());

    String body = templateEngine.process("BasicEmail.html", context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(body, true);
    mailSender.send(message);
  }

  /**
   * Sends registration email to the user
   *
   * @param to email address of the recipient
   * @param name name of the recipient
   * @throws MessagingException when something goes wrong
   */
  public void sendUserRegistrationEmail(String to, String name, String activationCode)
      throws MessagingException {
    Context context = new Context();
    context.setVariable("name", name);
    context.setVariable("year", LocalDate.now().getYear());
    context.setVariable("activation_code", activationCode);

    String body = templateEngine.process("UserRegistration.html", context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(to);
    helper.setSubject(String.format("Welcome %s!", name));
    helper.setText(body, true);
    mailSender.send(message);
  }

  /**
   * Sends an email to a user using a specified HTML template.
   *
   * <p>The template can include variables such as the user's full name, the current year, and an
   * optional detail string that will be available in the HTML context.
   *
   * @param to recipient email address
   * @param fullName full name of the recipient, used in the template
   * @param subject subject line of the email
   * @param templateName name of the HTML template to process
   * @param detail optional detail string available as a variable in the template
   * @throws MessagingException if the message could not be created or sent
   */
  public void sendMailTemplate(
      String to, String fullName, String subject, String templateName, String detail)
      throws MessagingException {
    Context context = new Context();
    context.setVariable("fullName", fullName);
    context.setVariable("year", LocalDate.now().getYear());
    context.setVariable("detail", detail);

    String body = templateEngine.process(templateName, context);

    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(body, true);
    mailSender.send(message);
  }
}
