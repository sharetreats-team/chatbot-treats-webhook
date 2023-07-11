package com.sharetreats.chatbot.module.controller.webhook;


import com.sharetreats.chatbot.infra.config.RestTemplateConfig;
import com.sharetreats.chatbot.module.controller.dto.retryDtos.RetryButton;
import com.sharetreats.chatbot.module.controller.dto.retryDtos.RetryKeyboard;
import com.sharetreats.chatbot.module.controller.dto.retryDtos.RetryMessage;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeButton;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeKeyboard;
import com.sharetreats.chatbot.module.controller.dto.welcomeDtos.WelcomeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

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
        RetryMessage message = new RetryMessage(accountId,
                1,
                "text",
                "Your time has expired.\n" +
                        "If you want to get back into service, press the \"Retry\".",
                new RetryKeyboard("keyboard", false, "#FFFFFF",
                        Collections.singletonList(new RetryButton(6, 1, "#29A7D9", "reply", "retry", "Retry", "center", "middle", "large"))));


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("X-Viber-Auth-Token", token);
        HttpEntity<RetryMessage> httpEntity = new HttpEntity<>(message, httpHeaders);

        return restTemplateConfig.restTemplate().exchange(VIBER_SEND_MESSAGE_URL, HttpMethod.POST, httpEntity, String.class);
    }

}
