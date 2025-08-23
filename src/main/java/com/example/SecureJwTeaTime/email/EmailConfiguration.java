package com.example.SecureJwTeaTime.email;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

/**
 * Contains de configuration for the java mail sender
 *
 * <p>This configuration is meant to be used in the development process, as it makes use of mail dev
 */
@Component
public class EmailConfiguration {
  @Bean
  public JavaMailSender getMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("localhost");
    mailSender.setPort(1025);
    return mailSender;
  }

  @Bean
  public TemplateEngine getTemplateEngine() {
    return new TemplateEngine();
  }
}
