package com.uzum.cms.dto.response;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

public record CardResponse(

        Long id,
        UUID accountId,
        UUID userId,
        String cardNumber,
        String holderName,
        CardType cardType,
        Status status,
        LocalDate expiryDate
) {}
