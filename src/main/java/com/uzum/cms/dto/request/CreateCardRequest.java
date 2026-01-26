package com.uzum.cms.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateCardRequest(
        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotNull(message = "Account ID cannot be null")
        Long accountId,

        @NotNull(message = "Card number masked cannot be null")
        @Size(min = 4, max = 19, message = "Card number masked must be 4 to 19 characters")
        String cardNumberMasked,

        @NotNull(message = "Expiry date cannot be null")
        LocalDate expiryDate
) {}
