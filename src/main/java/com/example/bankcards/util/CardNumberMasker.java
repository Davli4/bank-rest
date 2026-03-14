package com.example.bankcards.util;

import org.springframework.stereotype.Component;

@Component
public class CardNumberMasker {
    private static final String MASK = "**** **** ****";
    private static final int LAST_DIGITS_COUNT = 4;

    public String mask(String cardNumber) {
        if(cardNumber == null || cardNumber.length() < LAST_DIGITS_COUNT) {
            return MASK + "****";
        }

        String lastDigits = cardNumber.substring(cardNumber.length() - LAST_DIGITS_COUNT);
        return  MASK + lastDigits;
    }

    public boolean isValid(String cardNumber) {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }
}
