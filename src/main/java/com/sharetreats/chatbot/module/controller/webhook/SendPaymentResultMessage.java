package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.controller.dto.WelcomeMessageDto;
import org.springframework.http.ResponseEntity;

public class SendPaymentResultMessage {

    public static ResponseEntity<?> execute(String callback) {
        return ResponseEntity.ok(
                WelcomeMessageDto.builder()
                        .text("예시를 위한 dto 입니다. 확인 후 삭제해주세요.")
                        .build());
    }
}
