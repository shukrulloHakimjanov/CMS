package com.uzum.cms.dto.response;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.Status;

import java.time.LocalDate;

public record CardResponse(

        Long id,
        Long accountId,
        Long userId,
        String cardNumber,
        String holderName,
        CardType cardType,
        Status status,
        LocalDate expiryDate
) {}
