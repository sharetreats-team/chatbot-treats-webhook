package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.service.ServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 예시를 위한 클래스입니다.
 */
@RequiredArgsConstructor
@Component
public class SendProductsOfBrand extends ResponseEvent {

    private final ServiceTest service;

    public ResponseEntity<?> execute(String callback) {
        service.test();
        return ResponseEntity.ok("테스트");
    }
}
