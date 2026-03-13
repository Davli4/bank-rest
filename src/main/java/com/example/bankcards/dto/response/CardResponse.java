package com.example.bankcards.dto.response;

import com.example.bankcards.entity.enums.CardStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CardResponse {
    private Long id;
    private String cardNumber;
    private String maskedCardNumber;
    private String owner;
    private LocalDate expiryDate;
    private CardStatus status;
    private BigDecimal balance;
}