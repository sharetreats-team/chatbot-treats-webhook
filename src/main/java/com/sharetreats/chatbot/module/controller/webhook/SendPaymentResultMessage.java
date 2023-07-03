package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.controller.dto.Dto;
import com.sharetreats.chatbot.module.service.ServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 예시를 위한 클래스입니다.
 */
@RequiredArgsConstructor
@Component
public class SendPaymentResultMessage extends ResponseEvent {

    private final ServiceTest service;

    @Override
    public ResponseEntity<?> execute(String callback) {
        service.test();
        return ResponseEntity.ok(
                Dto.builder()
                        .text("예시를 위한 dto 입니다. 확인 후 삭제해주세요.")
                        .build());
    }
}
