package com.uzum.cms.dto.request;

import com.uzum.cms.constant.enums.CardNetworkType;
import com.uzum.cms.constant.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CardRequest(

    @NotNull(message = "User ID cannot be null")
    UUID userId,

    @NotNull(message = "Account ID cannot be null")
    UUID accountId,

    @NotBlank(message = "Card holder name cannot be blank")
    @Size(min = 3, max = 100, message = "Card holder name must be between 3 and 100 characters")
    String holderName,

    @NotNull(message = "Card type cannot be null")
    CardNetworkType cardNetworkType,

    @NotNull(message = "Card type cannot be null")
    CardType cardType,

    @NotBlank(message = "PIN cannot be blank")
    @Pattern(regexp = "\\d{4}", message = "PIN must be exactly 4 digits")
    String pin
) {
}
