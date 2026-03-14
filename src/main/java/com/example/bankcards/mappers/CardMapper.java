package com.example.bankcards.mappers;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.BalanceResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import com.example.bankcards.util.CardNumberMasker;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CardMapper {

    private final CardNumberMasker cardNumberMasker;

    public CardMapper(CardNumberMasker cardNumberMasker) {
        this.cardNumberMasker = cardNumberMasker;
    }

    public CardResponse toCardResponse(Card card) {
        if (card == null) {
            return null;
        }

        CardResponse response = new CardResponse();
        response.setId(card.getId());
        response.setCardNumber(card.getCardNumber());
        response.setMaskedCardNumber(cardNumberMasker.mask(card.getCardNumber()));
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
                cardNumberMasker.mask(card.getCardNumber()),
                card.getBalance()
        );
    }
}