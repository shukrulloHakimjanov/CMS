package com.uzum.cms.service.impl;

import com.uzum.cms.constant.enums.Status;

import com.uzum.cms.dto.request.CreateCardRequest;
import com.uzum.cms.dto.response.CardDto;
import com.uzum.cms.entity.Card;
import com.uzum.cms.exception.CardNonValidException;
import com.uzum.cms.exception.CardNotFoundException;
import com.uzum.cms.mapper.CardMapper;
import com.uzum.cms.repository.CardRepository;
import com.uzum.cms.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Override
    public CardDto createCard(CreateCardRequest request) {
        Card card = cardMapper.toEntity(request);

        card.setCardNumberMasked(maskCardNumber(request.cardNumberMasked()));

        card.setCardHash(generateCardHash(request.cardNumberMasked()));

        card.setStatus(Status.ACTIVE);
        Card savedCard = cardRepository.save(card);
        log.info("Card created with ID {}", savedCard.getId());

        return cardMapper.toDto(savedCard);
    }

    @Override
    public CardDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));
        return cardMapper.toDto(card);
    }

    @Override
    public List<CardDto> getCardByUserId(Long userId) {
        List<Card> cards = cardRepository.findAllByUserId(userId); // returns list
        if (cards.isEmpty()) {
            throw new CardNotFoundException("No cards found for user with ID " + userId);
        }
        return cards.stream()
                .map(cardMapper::toDto)
                .toList();
    }


    @Override
    public CardDto updateCardStatus(Long cardId, Status request) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException("Card not found"));

        card.setStatus(request);
        Card updatedCard = cardRepository.save(card);
        log.info("Card ID {} status updated to {}", updatedCard.getId(), updatedCard.getStatus());

        return cardMapper.toDto(updatedCard);
    }

    private String maskCardNumber(String fullCardNumber) {
        if (fullCardNumber == null || fullCardNumber.length() < 4) {
            throw new CardNonValidException("Card number too short");
        }
        String last4 = fullCardNumber.substring(fullCardNumber.length() - 4);
        return "**** **** **** " + last4;
    }

    private String generateCardHash(String fullCardNumber) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String SECRET_SALT = "MySecretSalt123!";
            String input = fullCardNumber + SECRET_SALT;
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            // Convert bytes to hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating card hash", e);
        }
    }
}
