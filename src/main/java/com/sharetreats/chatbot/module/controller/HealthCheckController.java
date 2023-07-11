package com.sharetreats.chatbot.module.controller;

import com.sharetreats.chatbot.infra.config.WebhookConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class HealthCheckController {

    private final WebhookConfig webhookConfig;
    private boolean webhookSet = false;
    @GetMapping("/health")
    public String healthCheck() {
        if (!webhookSet) {
            webhookConfig.setWebhook();
            webhookSet = true;
        }
        return "health check";
    }
}
