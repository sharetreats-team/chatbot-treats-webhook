package com.sharetreats.chatbot.module.event;

import com.sharetreats.chatbot.infra.mail.EmailMessage;
import com.sharetreats.chatbot.infra.mail.EmailService;
import com.sharetreats.chatbot.module.entity.GiftHistory;
import com.sharetreats.chatbot.module.repository.GiftHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Component
@Async
public class GiftHistoryEmailListener {

    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    @EventListener
    public void handleSendEmailEvent(GiftHistoryEmailEvent event) {
        log.info("email send event success!!");

        GiftHistory giftHistory = event.getGiftHistory();

        try {
            sendEmailToReceiverAboutGift(giftHistory);

            log.info("success email send to {} ({})", giftHistory.getReceiverName(), giftHistory.getReceiverEmail());
        } catch (Exception e) {
            log.error("Failed email send, ({})", e.getMessage());
        }
    }

    private void sendEmailToReceiverAboutGift(GiftHistory giftHistory) {
        String message = makeEmailMessageByTemplateEngine(giftHistory);
        EmailMessage emailMessage = EmailMessage.builder()
                .to(giftHistory.getReceiverEmail())
                .subject("선물내역 이메일 수신")
                .message(message)
                .build();
        emailService.send(emailMessage);
    }

    private String makeEmailMessageByTemplateEngine(GiftHistory giftHistory) {
        Context context = new Context();
        context.setVariable("receiverName", giftHistory.getReceiverName());
        context.setVariable("senderName", giftHistory.getSender().getName());
        context.setVariable("giftCode", giftHistory.getGiftCode());
        context.setVariable("expirationDate", giftHistory.getExpirationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        context.setVariable("brandName", giftHistory.getProduct().getBrandName());
        context.setVariable("productName", giftHistory.getProduct().getName());
        context.setVariable("productImage", giftHistory.getProduct().getImage());
        context.setVariable("message", giftHistory.getMessage());
        return templateEngine.process("mail/gift-mail-to-receiver", context);
    }
}
