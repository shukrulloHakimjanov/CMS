package com.uzum.cms.dto.request;

import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CardRequest(

        @NotNull(message = "User ID cannot be null")
        Long userId,

        @NotNull(message = "Account ID cannot be null")
        Long accountId,

        @NotBlank(message = "Card holder name cannot be blank")
        @Size(min = 3, max = 100, message = "Card holder name must be between 3 and 100 characters")
        String holderName,

        @NotNull(message = "Card type cannot be null")
        CardType cardType,

        @NotNull(message = "Card type cannot be null")
        UserType userType,

        @NotBlank(message = "Pin cannot be blank")
        @Size(max = 4, message = "Pin must be 4 symbols")
        String pin
){
}
