package com.sharetreats.chatbot.infra.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class TokenConfig {
//
//    private final RedisTemplate<String, String> redisTemplate;
//    public void generateToken(String userId) {
//
//        String token = UUID.randomUUID().toString();
//        Duration expireDuration = Duration.ofMinutes(20);
//        redisTemplate.opsForValue().set(userId, token, expireDuration);
//
//    }
//    public boolean validateToken(String userId) {
//
//        try {
//            String token = redisTemplate.opsForValue().get(userId);
//            if (token != null) {
//                Long expireTime = redisTemplate.getExpire(userId);
//                if (expireTime != null && expireTime > 0) {
//                    return true;
//                }
//            }
//        } catch (IllegalArgumentException e) {
//            return false;
//        }
//        return false;
//    }
//}