package com.sharetreats.chatbot.module.controller.dto.brandDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private String receiver;
    private int min_api_version;
    private String type;
    private String text;
    private BrandKeyboard keyboard;

    public static BrandResponse of(String id, BrandKeyboard keyboard) {
        return new BrandResponse(id, 7, "text", "\uD83E\uDD73\uD83C\uDF89 Hello. Welcome to Viber Treats! We have various treats to send to your friends.\n" +
                "Feel free to tab the categories on the bottom and check what we have!", keyboard);
    }
}
