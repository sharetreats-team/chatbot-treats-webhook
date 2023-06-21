package com.sharetreats.chatbot.product;

import com.sharetreats.chatbot.product.dto.ViberTextRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    @PostMapping("/bot/test")
    public ResponseEntity<?> handelWebhook(@RequestBody ViberTextRequest request) {
        return ResponseEntity.ok(request.getText());
    }
}
