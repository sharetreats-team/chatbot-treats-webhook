package com.sharetreats.chatbot.infra.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Primary
@Component
public class ConsoleEmailService implements EmailService {

    @Override
    public void send(EmailMessage emailMessage) {
        log.info("sent email : {}", emailMessage.getMessage());
    }
}
