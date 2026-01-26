package com.uzum.cms.dto.response;

import com.uzum.cms.constant.enums.Status;
import java.time.LocalDate;

public record CardDto(
        Long id,
        Long userId,
        Long accountId,
        String cardNumberMasked,
        String cardHash,
        LocalDate expiryDate,
        Status status
) {}
