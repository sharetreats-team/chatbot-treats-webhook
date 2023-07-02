package com.sharetreats.chatbot.module.option;

public enum DiscountType implements EnumMapperType{
    RATE_DISCOUNT("비율 할인"),
    FIX_DISCOUNT("고정 할인");

    public String text;

    DiscountType(String text) {
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
