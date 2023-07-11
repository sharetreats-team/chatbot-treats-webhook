package com.sharetreats.chatbot.module.controller.webhook;

import org.springframework.http.ResponseEntity;

public abstract class ResponseEvent {

    public abstract ResponseEntity<?> execute(String callback);
}
