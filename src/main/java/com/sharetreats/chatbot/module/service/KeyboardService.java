package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.dto.ViberSimpleButton;
import com.sharetreats.chatbot.module.dto.ViberSimpleKeyboard;
import com.sharetreats.chatbot.module.entity.Product;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
public class KeyboardService {
    public KeyboardService() {
    }

    ViberSimpleKeyboard createPurchaseKeyboard(String receiverId, Long productId) {
        ViberSimpleButton nextButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("use point")
                .text("Confirm")
                .textSize("regular")
                .build();

        ViberSimpleButton wrongInfoButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("send treats " + productId)
                .text("Type it again")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .bgColor("#FFFFFF")
                .buttons(Arrays.asList(nextButton, wrongInfoButton))
                .build();

        return keyboard;
    }

    ViberSimpleKeyboard createNoDiscountKeyboard() {
        ViberSimpleButton nextButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("no discount")
                .text("No Code")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .bgColor("#FFFFFF")
                .buttons(Collections.singletonList(nextButton))
                .build();

        return keyboard;
    }

    ViberSimpleKeyboard createProductDetailKeyboard(Product product) {
        ViberSimpleButton sendTreatsButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("reply")
                .actionBody("send treats " + product.getId())
                .text("BUY")
                .textSize("regular")
                .build();

        ViberSimpleButton discountPlaceButton = ViberSimpleButton.builder()
                .columns(6)
                .rows(1)
                .bgColor("#29A7D9")
                .actionType("open-url")
                .actionBody(product.getDiscountShop())
                .text("GO DISCOUNT PLACE")
                .textSize("regular")
                .build();

        ViberSimpleKeyboard keyboard = ViberSimpleKeyboard.builder()
                .type("keyboard")
                .defaultHeight(false)
                .bgColor("#FFFFFF")
                .buttons(Arrays.asList(sendTreatsButton, discountPlaceButton))
                .build();

        return keyboard;
    }
}