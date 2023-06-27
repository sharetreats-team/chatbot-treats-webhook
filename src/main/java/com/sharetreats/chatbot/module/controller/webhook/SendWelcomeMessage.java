package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.controller.dto.Dto;
import org.springframework.http.ResponseEntity;

public class SendWelcomeMessage {

    public static ResponseEntity<?> execute() {
        return ResponseEntity.ok(
                Dto.builder()
                        .text("예시를 위한 dto 입니다. 확인 후 삭제해주세요.")
                        .build());
    }
}
