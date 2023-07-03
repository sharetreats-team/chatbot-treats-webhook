package com.sharetreats.chatbot.infra.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenConfig {
    private static final long TOKEN_EXPIRE_TIME = 30 * 60 * 1000;
    private static final ConcurrentHashMap<String, TokenInfo> tokenStore = new ConcurrentHashMap<>();
    public void generateToken(String userId) {
        String token = UUID.randomUUID().toString();
        long expireTime = System.currentTimeMillis() + TOKEN_EXPIRE_TIME;
        TokenInfo tokenInfo = new TokenInfo(token, expireTime);
        tokenStore.put(userId, tokenInfo);
    }

    public boolean validateToken(String userId) {
        TokenInfo tokenInfo = tokenStore.get(userId);
        if (tokenInfo != null && tokenInfo.getExpireTime() >= System.currentTimeMillis()) {
            return true;
        } else {
            tokenStore.remove(userId);
            return false;
        }
    }
    @Getter
    private class TokenInfo {
        private String token;
        private long expireTime;
        public TokenInfo(String token, long expireTime) {
            this.token = token;
            this.expireTime = expireTime;
        }
    }
}