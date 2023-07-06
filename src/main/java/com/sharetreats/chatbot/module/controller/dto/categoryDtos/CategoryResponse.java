package com.sharetreats.chatbot.module.controller.dto.categoryDtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private String receiver;
    private int min_api_version;
    private String type;
    private String text;
    private CategoryKeyboard keyboard;

    public static CategoryResponse of (String id, CategoryKeyboard keyboard) {
        return new CategoryResponse(id, 7, "text",
                "Feel free to tab the categories on the bottom and check what we have!", keyboard);
    }
}
