package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.infra.mail.EmailMessage;
import com.sharetreats.chatbot.infra.mail.EmailService;
import com.sharetreats.chatbot.module.dto.GiftHistoryRedisHash;
import com.sharetreats.chatbot.module.entity.Account;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import com.sharetreats.chatbot.module.entity.Product;
import com.sharetreats.chatbot.module.event.GiftHistoryEmailEvent;
import com.sharetreats.chatbot.module.repository.AccountRepository;
import com.sharetreats.chatbot.module.repository.GiftHistoryRepository;
import com.sharetreats.chatbot.module.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {

    private final AccountRepository accountRepository;
    private final ProductRepository productRepository;
    private final GiftHistoryRepository giftHistoryRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public Optional<GiftHistory> payWithPoints(String accountId) {
        Account sender = getAccount(accountId);
        GiftHistoryRedisHash priorGiftHistory = getGiftHistory(accountId);
        if (!isPayable(sender, priorGiftHistory)) return Optional.empty();

        GiftHistory finalGiftHistory = proceedPayment(sender, priorGiftHistory);
        GiftHistory giftHistory = giftHistoryRepository.save(finalGiftHistory);
        eventPublisher.publishEvent(new GiftHistoryEmailEvent(giftHistory));
        return Optional.of(giftHistory);
    }

    private GiftHistory proceedPayment(Account sender, GiftHistoryRedisHash gift) {
        sender.decreasePoint(gift.getPrice());
        Product product = getProduct(gift.getProductId());
        return gift.saveGiftHistoryWithSender(sender, product);
    }

    private GiftHistoryRedisHash getGiftHistory(String key) {
        return (GiftHistoryRedisHash) redisTemplate.opsForValue().get(key);
    }

    private static boolean isPayable(Account sender, GiftHistoryRedisHash gift) {
        return sender.getPoint() >= gift.getPrice();
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 제품 ID 입니다."));
    }

    private Account getAccount(String accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원 ID 입니다."));
    }
}
