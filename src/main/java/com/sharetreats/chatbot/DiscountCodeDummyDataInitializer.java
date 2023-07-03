package com.sharetreats.chatbot;

import com.sharetreats.chatbot.module.entity.DiscountCode;
import com.sharetreats.chatbot.module.option.DiscountType;
import com.sharetreats.chatbot.module.repository.DiscountCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DiscountCodeDummyDataInitializer implements CommandLineRunner {
    private final DiscountCodeRepository discountCodeRepository;

    @Autowired
    public DiscountCodeDummyDataInitializer(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    @Override
    public void run(String... args) {
        // 이미 더미데이터가 있는지 체크
        if (discountCodeRepository.count() == 0) {
            createDummyData();
        }
    }

    private void createDummyData() {
        // 더미데이터 생성 및 저장

        DiscountCode discountCode1 = new DiscountCode("CODE1", DiscountType.FIX_DISCOUNT, 100);
        DiscountCode discountCode2 = new DiscountCode("CODE2", DiscountType.FIX_DISCOUNT, 200);
        DiscountCode discountCode3 = new DiscountCode("CODE3", DiscountType.RATE_DISCOUNT, 10);
        DiscountCode discountCode4 = new DiscountCode("CODE4", DiscountType.RATE_DISCOUNT, 20);

        discountCodeRepository.save(discountCode1);
        discountCodeRepository.save(discountCode2);
        discountCodeRepository.save(discountCode3);
        discountCodeRepository.save(discountCode4);
    }
}
