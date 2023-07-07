package com.sharetreats.chatbot.module.controller.dto.retryDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RetryMessage {

    private String receiver;

    private int min_api_version;

    private String type;

    private String text;

    private RetryKeyboard keyboard;
}
