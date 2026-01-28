package com.uzum.cms.service;

import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.CardResponse;

import java.util.List;

public interface CardService {

    CardResponse createCard(CardRequest request);

    CardResponse getCardById(Long cardId);

    List<CardResponse> getCardByUserId(Long user);

    CardResponse updateCardStatus(Long cardId, UpdateCardStatus request);
}
