package com.example.bankcards.service;

import com.example.bankcards.dto.request.TransferRequest;
import com.example.bankcards.dto.response.MessageResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import com.example.bankcards.exception.CardBlockedException;
import com.example.bankcards.exception.InsufficientFundsException;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.exception.UnauthorizedException;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.util.SecurityUtils;
import com.example.bankcards.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransferService {
    private final CardRepository cardRepository;
    private final SecurityUtils securityUtils;
    private final ValidationUtils validationUtils;

    public MessageResponse transfer(TransferRequest transferRequest) {
        validationUtils.validateTransferAmount(transferRequest.getAmount());

        Card from = findById(transferRequest.getFromCardId());
        Card to = findById(transferRequest.getToCardId());

        Long currUserId = securityUtils.getCurrentUserId();
        if(!from.getUser().getId().equals(currUserId) || !to.getUser().getId().equals(currUserId)) {
            throw new UnauthorizedException("Both cards must belong to you");
        }

        validateCardStatus(from, to);

        if (from.getBalance().compareTo(transferRequest.getAmount()) < 0) {
            throw InsufficientFundsException.forTransfer(from.getBalance(), transferRequest.getAmount());
        }

        from.setBalance(from.getBalance().subtract(transferRequest.getAmount()));
        to.setBalance(to.getBalance().add(transferRequest.getAmount()));

        cardRepository.save(from);
        cardRepository.save(to);

        return new MessageResponse("Transfer successful");
    }

    private void validateCardStatus(Card fromCard, Card toCard) {
        if (fromCard.getStatus() != CardStatus.ACTIVE) {
            throw CardBlockedException.forTransfer(fromCard.getId());
        }
        if (toCard.getStatus() != CardStatus.ACTIVE) {
            throw new CardBlockedException("Destination card is not active");
        }
    }

    private Card findById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> ResourceNotFoundException.forCard(cardId));
    }
}