package com.example.bankcards.exception;

public class CardBlockedException extends RuntimeException {

    public CardBlockedException(String message) {
        super(message);
    }

    public static CardBlockedException forTransfer(Long cardId) {
        return new CardBlockedException("Card " + cardId + " is blocked. Cannot perform transfer");
    }
}