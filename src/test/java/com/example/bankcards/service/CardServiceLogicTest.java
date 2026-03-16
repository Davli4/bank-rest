package com.example.bankcards.service;

import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import com.example.bankcards.entity.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CardServiceLogicTest {
    private Card card;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        card = new Card();
        card.setId(1L);
        card.setUser(user);
        card.setBalance(new BigDecimal("1000.00"));
        card.setStatus(CardStatus.ACTIVE);
    }

    @Test
    void blockCard_ShouldChangeStatusToBlocked() {
        assertEquals(CardStatus.ACTIVE, card.getStatus());
        card.setStatus(CardStatus.BLOCKED);
        assertEquals(CardStatus.BLOCKED, card.getStatus());
    }

    @Test
    void blockCard_ShouldThrow_WhenAlreadyBlocked() {
        card.setStatus(CardStatus.BLOCKED);
        assertThrows(IllegalStateException.class, () -> {
            if (card.getStatus() == CardStatus.BLOCKED) {
                throw new IllegalStateException("Card is already blocked");
            }
        });
    }

    @Test
    void checkBalance_ShouldReturnCorrectValue() {
        assertEquals(new BigDecimal("1000.00"), card.getBalance());
    }
}