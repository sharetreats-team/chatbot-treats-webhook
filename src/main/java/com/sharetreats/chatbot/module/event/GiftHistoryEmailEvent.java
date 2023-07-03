package com.sharetreats.chatbot.module.event;

import com.sharetreats.chatbot.module.entity.GiftHistory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GiftHistoryEmailEvent {

    private final GiftHistory giftHistory;
}
