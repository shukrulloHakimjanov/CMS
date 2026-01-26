package com.uzum.cms.service;

import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.dto.request.CreateCardRequest;
import com.uzum.cms.dto.response.CardDto;

import java.util.List;

public interface CardService {

    CardDto createCard(CreateCardRequest request);

    CardDto getCardById(Long cardId);

    List<CardDto> getCardByUserId(Long user);

    CardDto updateCardStatus(Long cardId, Status request);
}
