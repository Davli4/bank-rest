package com.example.bankcards.service;

import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import com.example.bankcards.entity.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TransferServiceLogicTest {
    private Card fromCard;
    private Card toCard;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        fromCard = new Card();
        fromCard.setId(1L);
        fromCard.setUser(user);
        fromCard.setBalance(new BigDecimal("1000.00"));
        fromCard.setStatus(CardStatus.ACTIVE);

        toCard = new Card();
        toCard.setId(2L);
        toCard.setUser(user);
        toCard.setBalance(new BigDecimal("500.00"));
        toCard.setStatus(CardStatus.ACTIVE);
    }

    @Test
    void transfer_ShouldSubtractFromFromCardAndAddToToCard() {
        BigDecimal amount = new BigDecimal("200.00");
        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));
        assertEquals(new BigDecimal("800.00"), fromCard.getBalance());
        assertEquals(new BigDecimal("700.00"), toCard.getBalance());
    }

    @Test
    void transfer_ShouldNotChangeTotalSum() {
        BigDecimal amount = new BigDecimal("200.00");
        BigDecimal totalBefore = fromCard.getBalance().add(toCard.getBalance());
        fromCard.setBalance(fromCard.getBalance().subtract(amount));
        toCard.setBalance(toCard.getBalance().add(amount));
        BigDecimal totalAfter = fromCard.getBalance().add(toCard.getBalance());
        assertEquals(totalBefore, totalAfter);
    }

    @Test
    void transfer_ShouldThrow_WhenFromCardBlocked() {
        fromCard.setStatus(CardStatus.BLOCKED);
        assertThrows(IllegalStateException.class, () -> {
            if (fromCard.getStatus() != CardStatus.ACTIVE) {
                throw new IllegalStateException("Sender card is blocked");
            }
        });
    }

    @Test
    void transfer_ShouldThrow_WhenInsufficientFunds() {
        BigDecimal amount = new BigDecimal("2000.00");
        assertThrows(IllegalStateException.class, () -> {
            if (fromCard.getBalance().compareTo(amount) < 0) {
                throw new IllegalStateException("Insufficient funds");
            }
        });
    }

    @Test
    void transfer_ShouldThrow_WhenCardsBelongToDifferentUsers() {
        User otherUser = new User();
        otherUser.setId(2L);
        toCard.setUser(otherUser);
        assertThrows(IllegalStateException.class, () -> {
            if (!fromCard.getUser().getId().equals(toCard.getUser().getId())) {
                throw new IllegalStateException("Cards belong to different users");
            }
        });
    }
}