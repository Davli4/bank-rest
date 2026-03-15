package com.example.bankcards.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }

    public static InsufficientFundsException forTransfer(BigDecimal balance, BigDecimal amount) {
        return new InsufficientFundsException(
                String.format("Insufficient funds. Balance: %.2f, Requested: %.2f",
                        balance, amount)
        );
    }
}