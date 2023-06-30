package com.sharetreats.chatbot.module.option;

public enum Availability implements EnumMapperType{
    AVAILABLE("사용 가능"),
    UNAVAILABLE("사용 불가");

    public String text;

    Availability(String text) {
        this.text = text;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getText() {
        return text;
    }
}
