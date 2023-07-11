package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.ViberTextWithKeyboardMessage;
import com.sharetreats.chatbot.module.entity.Product;
import org.springframework.http.ResponseEntity;

public interface ProductDetailService {

    ViberTextWithKeyboardMessage makeMessage(String receiverId, Product product);
    ResponseEntity<?> sendMessage(String receiverId, Long productId, String authToken);
    ResponseEntity<ViberTextWithKeyboardMessage> getSendTextWithKeyboardResponse(String authToken, ViberTextWithKeyboardMessage viberTextWithKeyboardMessage);
}
