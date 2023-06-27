package com.sharetreats.chatbot.product;

import com.sharetreats.chatbot.module.dto.ViberCallbackData;
import com.sharetreats.chatbot.module.service.RichMediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    String viber_auth_token = "512b538aa327e284-b4accf19ae3e79ad-cffb22f50a17ca97";
    private final RichMediaService richMediaService;

    public ProductController(RichMediaService richMediaService) {
        this.richMediaService = richMediaService;
    }

    @PostMapping("/test")
    public ResponseEntity<?> connect(@RequestBody ViberCallbackData callbackData, HttpServletRequest httpServletRequest) {
        String callback_sender_id = null;
        String callback_tracking_data = null;
        String callback_message = "";

        if (callbackData != null && callbackData.getSender() != null) {
            callback_sender_id = callbackData.getSender().getId();
            callback_tracking_data = callbackData.getMessage().getTracking_data();
            callback_message = callbackData.getMessage().getText();
        }

        return richMediaService.sendProductsByBrand(callback_message, viber_auth_token, callback_sender_id);
    }
}
