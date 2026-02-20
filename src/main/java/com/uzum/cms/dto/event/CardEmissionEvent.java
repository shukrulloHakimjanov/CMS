package com.uzum.cms.dto.event;

import com.uzum.cms.constant.enums.CardNetworkType;
import com.uzum.cms.constant.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CardEmissionEvent(@NotNull UUID userId, @NotNull UUID accountId, @NotBlank String holderName,
                                @NotNull CardNetworkType cardNetworkType, @NotNull CardType cardType,
                                @NotBlank String pinEncrypted) {}
