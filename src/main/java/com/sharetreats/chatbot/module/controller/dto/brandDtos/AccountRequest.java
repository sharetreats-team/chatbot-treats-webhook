package com.sharetreats.chatbot.module.controller.dto.brandDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String id;
    private String name;
    private String avatar;
}
