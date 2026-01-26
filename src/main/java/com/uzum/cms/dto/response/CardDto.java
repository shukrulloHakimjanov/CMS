package com.uzum.cms.dto.response;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.Status;
import com.uzum.cms.constant.enums.UserType;

import java.time.LocalDate;

public record CardDto(
        Long id,
        Long userId,
        Long accountId,
        Long accountNumber,
        String cardNumberMasked,
        String cardHash,
        LocalDate expiryDate,
        Status status,
        CardType cardType,
        UserType userType,
        String token
) {}
