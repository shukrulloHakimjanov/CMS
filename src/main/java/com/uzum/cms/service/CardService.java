package com.uzum.cms.service;

import com.uzum.cms.dto.PageRequestDto;
import com.uzum.cms.dto.request.CardRequest;
import com.uzum.cms.dto.request.UpdateCardStatus;
import com.uzum.cms.dto.response.CardResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CardService {

    CardResponse createCard(CardRequest request);

    CardResponse getCardById(Long cardId);

    Page<CardResponse> getCardsByUserId(Long userId, PageRequestDto pageRequest);

    CardResponse updateCardStatus(Long cardId, UpdateCardStatus request);
}
