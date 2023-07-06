package com.sharetreats.chatbot.module.controller.webhook;


import com.sharetreats.chatbot.infra.config.RestTemplateConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class SendInvalidTokenMessage {

    private final RestTemplateConfig restTemplateConfig;

    private final SendWelcomeMessage sendWelcomeMessage;
    @Value("${viber.auth.token}")
    private String token;
    static final String VIBER_SEND_MESSAGE_URL = "https://chatapi.viber.com/pa/send_message";

    public ResponseEntity<?> execute(String accountId) {
        log.info("send invalid message");
        InvalidMessage invalidMessage = new InvalidMessage(accountId, "text", "stchatbot3", "Validity time has expired");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-Viber-Auth-Token", token);

        HttpEntity<InvalidMessage> invalidMessageHttpEntity = new HttpEntity<>(invalidMessage, httpHeaders);
        restTemplateConfig.restTemplate().exchange(VIBER_SEND_MESSAGE_URL, HttpMethod.POST, invalidMessageHttpEntity, String.class);
        sendWelcomeMessage.execute();
        return ResponseEntity.ok().build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private class InvalidMessage {

        private String receiver;
        private String type;
        private String name;
        private String text;
    }
}
