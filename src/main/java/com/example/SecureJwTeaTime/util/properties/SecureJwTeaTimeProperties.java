package com.example.SecureJwTeaTime.util.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for the SecureJwTeaTime application.
 *
 * <p>This class set the values from the application.properties file with the prefix securejwteatime
 * to the variables
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "securejwteatime")
public class SecureJwTeaTimeProperties {

  /** The base URL of the frontend client */
  private String client;

  /** The base URL of the backend/domain */
  private String domain;
}
