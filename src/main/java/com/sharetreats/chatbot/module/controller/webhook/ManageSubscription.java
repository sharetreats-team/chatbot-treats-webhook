package com.sharetreats.chatbot.module.controller.webhook;

import com.sharetreats.chatbot.module.controller.dto.brandDtos.AccountRequest;
import com.sharetreats.chatbot.module.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ManageSubscription {

    private final AccountService accountService;

    public void validateAccount(String callback) {
        accountService.validateAccount(convertToSender(callback));
    }

    public void validateReSubscription(String callback) {
        accountService.validateAccount(convertToUser(callback));
    }

    public void unsubscribe(String callback) {
        accountService.unsubscribe(getIdFromCallback(callback));
    }

    private static String getIdFromCallback(String callback) {
        return new JSONObject(callback).getString("user_id");
    }

    private AccountRequest convertToSender(String callback) {
        JSONObject sender = new JSONObject(callback).getJSONObject("sender");
        return new AccountRequest(sender.getString("id"), sender.getString("name"), sender.getString("avatar"));
    }

    private AccountRequest convertToUser(String callback) {
        JSONObject sender = new JSONObject(callback).getJSONObject("user");
        return new AccountRequest(sender.getString("id"), sender.getString("name"), sender.getString("avatar"));
    }
}
