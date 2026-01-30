package com.uzum.cms.service.impl;

import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.dto.PageRequestDto;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.entity.CardEntity;
import com.uzum.cms.exception.CardNotFoundException;
import com.uzum.cms.mapper.CardMapper;
import com.uzum.cms.repository.CardRepository;
import com.uzum.cms.service.CardService;
import com.uzum.cms.utility.UtilitiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


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
        CardEntity cardEntity = cardMapper.toEntity(request);

        String cardNumber = utilitiesService.generateCardNumber();

        cardEntity.setCardNumber(cardNumber);
        cardEntity.setToken(utilitiesService.generateToken(cardNumber));
        cardEntity.setExpiryDate(LocalDate.now().plusYears(10));
        cardEntity.setStatus(Status.ACTIVE);
        cardEntity.setCcv(utilitiesService.generateCcv());

        CardEntity saved = cardRepository.save(cardEntity);

        return cardMapper.toDto(saved);
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
    public Page<CardResponse> getCardsByUserId(Long userId, PageRequestDto pageRequest) {
        Pageable pageable = pageRequest.getPageable();

        Page<CardEntity> cardsPage = cardRepository.findAllByUserId(userId, pageable);

        if (cardsPage.isEmpty()) {
            throw new CardNotFoundException("No cards found for user with ID " + userId);
        }

        return cardsPage.map(cardMapper::toDto);
    }

    @Override
    @Transactional
    public CardResponse updateCardStatus(Long cardId, UpdateCardStatus request) {
        CardEntity cardEntity = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        cardEntity.setStatus(request.status());
        CardEntity updatedCardEntity = cardRepository.save(cardEntity);

        log.info("Card ID {} status updated to {}", updatedCardEntity.getId(), updatedCardEntity.getStatus());
        return cardMapper.toDto(updatedCardEntity);
    }
}
