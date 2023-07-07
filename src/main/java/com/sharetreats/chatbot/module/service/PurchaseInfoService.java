package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.GiftHistoryRedisHash;
import com.sharetreats.chatbot.module.dto.ViberTextMessage;
import com.sharetreats.chatbot.module.dto.ViberTextWithKeyboardMessage;
import org.springframework.http.ResponseEntity;

public interface PurchaseInfoService {
    ViberTextWithKeyboardMessage makeMessage(String receiverId, GiftHistoryRedisHash giftHistoryRedisHash);
    ResponseEntity<?> sendMessage(String receiverId, String messageText, String trackingData, String authToken);
    ResponseEntity<ViberTextMessage> getSendTextResponse(String authToken, ViberTextMessage viberTextMessage);
    ResponseEntity<ViberTextWithKeyboardMessage> getSendTextWithKeyboardResponse(String authToken, ViberTextWithKeyboardMessage viberTextWithKeyboardMessage);
}
