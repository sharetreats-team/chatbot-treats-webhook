package com.sharetreats.chatbot.module.option;

public enum GiftAvailability implements EnumMapperType {

    AVAILABLE("사용가능"),
    UNAVAILABLE("사용불가능");

    public String text;

    GiftAvailability(String text) {
        this.text = text;
    }

    @Override
    public String getKey() {
        return null;
    }

    @Override
    public String getText() {
        return null;
    }
}
