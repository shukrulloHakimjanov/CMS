package com.uzum.cms.service.impl;

import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.dto.PageRequestDto;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.AMSInfoResponse;
import com.uzum.cms.dto.response.CardInfoResponse;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.entity.CardEntity;
import com.uzum.cms.exception.CardNotFoundException;
import com.uzum.cms.mapper.CardMapper;
import com.uzum.cms.repository.CardRepository;
import com.uzum.cms.service.AmsIntegrationService;
import com.uzum.cms.service.CardService;
import com.uzum.cms.utility.UtilitiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UtilitiesService utilitiesService;
    private final CardEventProducer cardEventProducer;
    private final AmsIntegrationService amsIntegrationService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void startCardEmission(CardRequest request) {
        String encryptedPin = passwordEncoder.encode(request.pin());

        CardEmissionEvent event = cardMapper.requestToEvent(request, encryptedPin);

        cardEventProducer.publishForUserValidation(event);
    }

    @Override
    @Transactional
    public CardResponse createCard(CardEmissionEvent event) {
        String cardNumber = utilitiesService.generateCardNumber();
        String token = utilitiesService.generateToken(cardNumber);
        String cvv = utilitiesService.generateCvv();
        LocalDate expiryDate = utilitiesService.generateExpiryDate();

        CardEntity cardEntity = cardMapper.toEntity(event, cardNumber, cvv, token, expiryDate);

        CardEntity saved = cardRepository.save(cardEntity);

        return cardMapper.toDto(saved);
    }

    private CardEntity getCardById(final Long id) {
        return cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException(Error.CARD_NOT_FOUND_CODE));
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse getCardResponseById(Long cardId) {
        return cardMapper.toDto(getCardById(cardId));
    }

    @Override
    @Transactional(readOnly = true)
    public CardInfoResponse getByToken(final String token) {
        CardEntity cardEntity = cardRepository.findByToken(token).orElseThrow(() -> new CardNotFoundException(Error.CARD_NOT_FOUND_CODE));

        AMSInfoResponse amsInfoResponse = amsIntegrationService.fetchAccountInfoById(cardEntity.getAccountId());

        return cardMapper.entityToCardInfoResponse(cardEntity, amsInfoResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardResponse> getCardsByUserId(Long userId, PageRequestDto pageRequest) {
        Pageable pageable = pageRequest.getPageable();

        Page<CardEntity> cardsPage = cardRepository.findAllByUserId(userId, pageable);

        return cardsPage.map(cardMapper::toDto);
    }

    @Override
    @Transactional
    public CardResponse updateCardStatus(Long cardId, UpdateCardStatus request) {
        CardEntity cardEntity = getCardById(cardId);

        cardEntity.setStatus(request.status());
        CardEntity updatedCardEntity = cardRepository.save(cardEntity);

        log.info("Card ID {} status updated to {}", updatedCardEntity.getId(), updatedCardEntity.getStatus());

        return cardMapper.toDto(updatedCardEntity);
    }
}
