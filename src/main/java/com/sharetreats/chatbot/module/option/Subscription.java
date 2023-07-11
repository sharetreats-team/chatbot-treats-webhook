package com.sharetreats.chatbot.module.option;

public enum Subscription implements EnumMapperType{

    SUBSCRIBE("구독중"),
    UNSUBSCRIBE("구독 취소");

    public String text;

    Subscription(String text) {
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
