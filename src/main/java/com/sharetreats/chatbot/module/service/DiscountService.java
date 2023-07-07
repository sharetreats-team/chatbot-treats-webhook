package com.sharetreats.chatbot.module.service;

import com.sharetreats.chatbot.module.entity.DiscountCode;

public interface DiscountService {
    DiscountCode validateDiscountCode(String discountCode);
    int calculateDiscount(DiscountCode discountCode, int originalPrice);
}
