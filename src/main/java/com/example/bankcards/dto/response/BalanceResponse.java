package com.example.bankcards.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceResponse {
    private Long cardId;
    private String maskedCardNumber;
    private BigDecimal balance;
}