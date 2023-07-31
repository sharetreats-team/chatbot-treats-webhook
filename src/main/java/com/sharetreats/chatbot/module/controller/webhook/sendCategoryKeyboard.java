package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.infra.config.RestTemplateConfig;
import com.sharetreats.chatbot.module.controller.dto.categoryDtos.CategoryKeyboard;
import com.sharetreats.chatbot.module.controller.dto.categoryDtos.CategoryResponse;
import com.sharetreats.chatbot.module.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class sendCategoryKeyboard {
    private final CategoryService categoryService;
    private final RestTemplateConfig restTemplateConfig;
    static final String VIBER_SEND_MESSAGE_URL = "https://chatapi.viber.com/pa/send_message";
    @Value("${viber.auth.token}")
    private String token;

    public ResponseEntity<?> execute(String callback) {
        CategoryResponse response = findCategories(getIdFromSender(callback));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Viber-Auth-Token", token);

        HttpEntity<CategoryResponse> httpEntity = new HttpEntity<>(response, headers);
        ResponseEntity<String> restResponse = restTemplateConfig.restTemplate().postForEntity(VIBER_SEND_MESSAGE_URL, httpEntity, String.class);
        return restResponse;
    }

    private CategoryResponse findCategories(String id) {
        CategoryKeyboard keyboard = CategoryKeyboard.of(categoryService.createCategoryButtons());
        return CategoryResponse.of(id, keyboard);
    }

    private static String getIdFromSender(String callback) {
        return new JSONObject(callback).getJSONObject("sender").getString("id");
    }
}
