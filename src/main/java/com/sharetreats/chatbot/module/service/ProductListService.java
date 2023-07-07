package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.ViberRichMediaMessage;
import com.sharetreats.chatbot.module.entity.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductListService {
    ViberRichMediaMessage makeMessage(List<Product> products);
    ResponseEntity<?> sendMessage(String receiverId, Long brandId, String auth_token);
    ResponseEntity<?> getRichMediaResponse(String receiverId, String authToken, ViberRichMediaMessage richMediaMessage);
}
