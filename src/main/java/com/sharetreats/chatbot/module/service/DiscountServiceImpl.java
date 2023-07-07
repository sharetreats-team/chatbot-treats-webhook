package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.entity.DiscountCode;
import com.sharetreats.chatbot.module.option.DiscountType;
import com.sharetreats.chatbot.module.repository.DiscountCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountCodeRepository discountCodeRepository;

    public DiscountServiceImpl(DiscountCodeRepository discountCodeRepository) {
        this.discountCodeRepository = discountCodeRepository;
    }

    @Override
    public DiscountCode validateDiscountCode(String discountCode) {
        return discountCodeRepository.findByCode(discountCode);
    }

    @Override
    public int calculateDiscount(DiscountCode discountCode, int originalPrice) {
        if (discountCode.getType() == DiscountType.FIX_DISCOUNT) {
            return originalPrice - discountCode.getDiscountPrice();
        }
        if (discountCode.getType() == DiscountType.RATE_DISCOUNT) {
            double discountPercentage = discountCode.getDiscountPrice() / 100.0;
            double discountAmount = originalPrice * discountPercentage;
            return (int) Math.round(originalPrice - discountAmount);
        }
        return originalPrice;
    }
}
