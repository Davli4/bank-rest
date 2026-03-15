package com.example.bankcards.service;

import com.example.bankcards.dto.request.CardRequest;
import com.example.bankcards.dto.response.BalanceResponse;
import com.example.bankcards.dto.response.CardResponse;
import com.example.bankcards.entity.enums.CardStatus;
import com.example.bankcards.entity.model.Card;
import com.example.bankcards.entity.model.User;
import com.example.bankcards.exception.CardBlockedException;
import com.example.bankcards.exception.ResourceNotFoundException;
import com.example.bankcards.exception.UnauthorizedException;
import com.example.bankcards.mappers.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final CardMapper cardMapper;
    private final SecurityUtils securityUtils;

    public Page<CardResponse> getCards(Pageable pageable) {
        Long carUserId = securityUtils.getCurrentUserId();
        return cardRepository.findByUserId(carUserId, pageable).map(cardMapper::toCardResponse);
    }

    public CardResponse getCardById(Long cardId) {
        Card card = findById(cardId);
        securityUtils.checkCardOwnership(card.getUser().getId());
        return cardMapper.toCardResponse(card);
    }

    public BalanceResponse getCardBalance(Long cardId) {
        Card card = findById(cardId);
        securityUtils.checkCardOwnership(card.getUser().getId());
        return cardMapper.toBalanceResponse(card);
    }

    @Transactional
    public CardResponse blockCard(Long cardId) {
        Card card = findById(cardId);
        securityUtils.checkCardOwnership(card.getUser().getId());

        if(card.getStatus() == CardStatus.BLOCKED) {
            throw new CardBlockedException("Card is already blocked");
        }

        card.setStatus(CardStatus.BLOCKED);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    public Page<CardResponse> getAllCardsA(Long userId, CardStatus cardStatus, String owner, Pageable pageable) {
        if(!securityUtils.isAdmin()) {
            throw UnauthorizedException.adminOnly();
        }

        return cardRepository.findWithFilters(userId, cardStatus, owner, pageable).map(cardMapper::toCardResponse);
    }

    @Transactional
    public CardResponse createCard(CardRequest cardRequest, Long userId) {
        if(!securityUtils.isAdmin()) {
            throw UnauthorizedException.adminOnly();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> ResourceNotFoundException.forUser(userId));

        if(cardRepository.existsByCardNumber(cardRequest.getCardNumber())) {
            throw new IllegalArgumentException("Card number already exists");
        }

        Card card = cardMapper.toCardEntity(cardRequest);
        card.setUser(user);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Transactional
    public CardResponse activateCard(Long cardId) {
        if(!securityUtils.isAdmin()) {
            throw UnauthorizedException.adminOnly();
        }

        Card card = findById(cardId);
        card.setStatus(CardStatus.ACTIVE);
        return cardMapper.toCardResponse(cardRepository.save(card));
    }

    @Transactional
    public void deleteCard(Long cardId) {
        if(!securityUtils.isAdmin()) {
            throw UnauthorizedException.adminOnly();
        }

        Card card = findById(cardId);
        cardRepository.delete(card);
    }

    public CardResponse getCardByIdAndUser(Long cardId) {
        Long currUserId = securityUtils.getCurrentUserId();

        Card card = cardRepository.findByIdAndUserId(cardId,currUserId)
                .orElseThrow(() -> ResourceNotFoundException.forUser(currUserId));

        return cardMapper.toCardResponse(card);
    }

    public BalanceResponse getCardBalanceIfOwner(Long cardId) {
        Long currUserId = securityUtils.getCurrentUserId();

        Card card = cardRepository.findByIdAndUserId(cardId, currUserId)
                .orElseThrow(() -> ResourceNotFoundException.forCard(cardId));

        return cardMapper.toBalanceResponse(card);
    }

    public List<CardResponse> getCardsByStatus(CardStatus status) {
        Long currUserId = securityUtils.getCurrentUserId();

        List<Card> cards = cardRepository.findByUserIdAndStatus(currUserId, status);

        return cardMapper.toCardResponseList(cards);
    }

    private Card findById(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> ResourceNotFoundException.forCard(cardId));
    }
}