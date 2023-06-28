package com.sharetreats.chatbot.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Slf4j
@RequiredArgsConstructor
@Component
public class WebhookConfig {

    private final RestTemplateConfig restTemplateConfig;
    @Value("${viber.auth.token}")
    private String authToken;
    private String webhookUrl = "https://chatapi.viber.com/pa/set_webhook";
    private String payload = "https://stchatbot.site";

    @SneakyThrows
    public void setWebhook()  {
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(createWebHookParams());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-Viber-Auth-Token", authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);

        ResponseEntity<String> response = restTemplateConfig.restTemplate().exchange(webhookUrl, HttpMethod.POST, httpEntity, String.class);
        log.info("Body {}", response.getBody());

    }

    private Map<String, Object> createWebHookParams() {
        return Map.of(
                "url", payload,
                "send_name", true,
                "send_photo", true,
                "event_types", getEvents()
        );
    }
    private List<String> getEvents() {
        return List.of("delivered", "seen", "failed", "subscribed", "unsubscribed", "conversation_started", "message");
    }
}
