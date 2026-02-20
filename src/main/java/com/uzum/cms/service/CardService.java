package com.uzum.cms.service;

import com.uzum.cms.dto.PageRequestDto;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.CardInfoResponse;
import com.uzum.cms.dto.response.CardResponse;
import org.springframework.data.domain.Page;

import java.util.Currency;
import java.util.List;

public interface CardService {

    void startCardEmission(CardRequest request);

    CardResponse createCard(CardEmissionEvent event);

    CardResponse getCardResponseById(Long cardId);

    void validateByTokenAndCurrency(String token, Currency currency);

    Page<CardResponse> getCardsByUserId(Long userId, PageRequestDto pageRequest);

    CardResponse updateCardStatus(Long cardId, UpdateCardStatus request);
}
