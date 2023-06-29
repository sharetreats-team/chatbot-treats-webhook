package com.sharetreats.chatbot.module.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WelcomeMessage {
    private WelcomeSender sender;
    private String tracking_data;
    private String type;
    private String text;
    private WelcomeKeyboard keyboard;
    private String media;
    private String thumbnail;

    public static WelcomeMessage of (String name, String avatar, String tracking_data, String type, String text,
                                     WelcomeKeyboard welcomeKeyboard, String media, String thumbnail) {
        return new WelcomeMessage(new WelcomeSender(name, avatar), tracking_data, type,
                text, WelcomeKeyboard.of(welcomeKeyboard.getType(), welcomeKeyboard.isDefaultHeight(), welcomeKeyboard.getBgColor(), welcomeKeyboard.getButtons()), media, thumbnail);
    }

}
