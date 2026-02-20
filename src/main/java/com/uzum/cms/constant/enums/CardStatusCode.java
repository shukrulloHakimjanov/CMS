package com.uzum.cms.constant.enums;

import lombok.Getter;

@Getter
public enum CardStatusCode {
    SUCCESS(20000, "Success"),
    ACCOUNT_VALIDATION_FAILED(20100, "Account validation failed"),
    USER_VALIDATION_FAILED(20200, "User validation failed"),
    CARD_CREATION_FAILED(20300, "Card creation failed");

    final int code;
    final String message;

    CardStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
