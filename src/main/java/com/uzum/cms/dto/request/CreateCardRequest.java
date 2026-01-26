package com.uzum.cms.dto.request;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateCardRequest(

        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotNull(message = "Account ID cannot be null")
        Long accountId,

        @NotNull(message = "Account number cannot be null")
        Long accountNumber,  // new field

        @NotNull(message = "Card number masked cannot be null")
        @Size(min = 4, max = 19, message = "Card number masked must be 4 to 19 characters")
        String cardNumberMasked,

        @NotNull(message = "Expiry date cannot be null")
        LocalDate expiryDate,

        CardType cardType,
        UserType userType,

        String token
) {}
