package com.example.SecureJwTeaTime.events.email;

import java.util.UUID;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EmailEvent extends ApplicationEvent {
    private final String email;
    private final UUID userId;
    private final String name;
    private final String subject;
    private final String template;
    private final String detail;

    public EmailEvent(Object source, String email, UUID userId, String name, String subject, String template, String detail) {
        super(source);
        this.email = email;
        this.userId = userId;
        this.name = name;
        this.subject = subject;
        this.template = template;
        this.detail = detail;
    }
}
