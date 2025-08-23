package com.example.SecureJwTeaTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SecureJwTeaTimeApplication {

  public static void main(String[] args) {
    SpringApplication.run(SecureJwTeaTimeApplication.class, args);
  }
}
