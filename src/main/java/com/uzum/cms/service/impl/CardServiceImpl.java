package com.uzum.cms.service.impl;

import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.entity.Card;
import com.uzum.cms.exception.CardNotFoundException;
import com.uzum.cms.mapper.CardMapper;
import com.uzum.cms.repository.CardRepository;
import com.uzum.cms.service.CardService;
import com.uzum.cms.utility.UtilitiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UtilitiesService utilitiesService;

    @Override
    @Transactional
    public CardResponse createCard(CardRequest request) {

        Card card = cardMapper.toEntity(request);

        String fullCardNumber = utilitiesService.generateCardNumber(); // 0820XXXXXXXXXXXX
        card.setCardNumber(fullCardNumber);
        card.setToken(utilitiesService.generateToken(fullCardNumber));
        card.setExpiryDate(LocalDate.now().plusYears(10));
        card.setStatus(Status.ACTIVE);
        card.setCcv(utilitiesService.generateCcv());

        Card savedCard = cardRepository.save(card);
        log.info("Card created with ID {}", savedCard.getId());

        return cardMapper.toDto(savedCard);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse getCardById(Long cardId) {
        return cardRepository.findById(cardId)
                .map(cardMapper::toDto)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));
    }


    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> getCardByUserId(Long userId) {
        List<Card> cards = cardRepository.findAllByUserId(userId);
        if (cards.isEmpty()) {
            throw new CardNotFoundException("No cards found for user with ID " + userId);
        }
        return cards.stream()
                .map(cardMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public CardResponse updateCardStatus(Long cardId, UpdateCardStatus request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        card.setStatus(request.status());
        Card updatedCard = cardRepository.save(card);

        log.info("Card ID {} status updated to {}", updatedCard.getId(), updatedCard.getStatus());
        return cardMapper.toDto(updatedCard);
    }
}
