package com.example.bankcards.mappers;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.BalanceResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardMapper {

    public CardResponse toCardResponse(Card card) {
        if (card == null) {
            return null;
        }

        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setCardNumber(card.getCardNumber());
        response.setMaskedCardNumber(maskCardNumber(card.getCardNumber()));
        response.setOwner(card.getOwner());
        response.setExpiryDate(card.getExpiryDate());
        response.setStatus(card.getStatus());
        response.setBalance(card.getBalance());

        return response;
    }

    public List<CardResponse> toCardResponseList(List<Card> cards) {
        return cards.stream()
                .map(this::toCardResponse)
                .collect(Collectors.toList());
    }

    public Card toCardEntity(CardRequest request) {
        if (request == null) {
            return null;
        }

        Card card = new Card();
        card.setCardNumber(request.getCardNumber());
        card.setOwner(request.getOwner());
        card.setExpiryDate(request.getExpiryDate());
        card.setBalance(request.getInitialBalance() != null ?
                request.getInitialBalance() : java.math.BigDecimal.ZERO);
        card.setStatus(CardStatus.ACTIVE);

        return card;
    }

    public BalanceResponse toBalanceResponse(Card card) {
        if (card == null) {
            return null;
        }

        return new BalanceResponse(
                card.getId(),
                maskCardNumber(card.getCardNumber()),
                card.getBalance()
        );
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "**** **** **** ****";
        }
        String last4 = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + last4;
    }
}