package com.uzum.cms.constant.enums;

import lombok.Getter;

@Getter
public enum Error {

    INTERNAL_SERVICE_ERROR_CODE(10001, "System not available"),
    EXTERNAL_SERVICE_FAILED_ERROR_CODE(10002, "External service not available"),
    HANDLER_NOT_FOUND_ERROR_CODE(10003, "Handler not found"),
    JSON_NOT_VALID_ERROR_CODE(10004, "Json not valid"),
    VALIDATION_ERROR_CODE(10005, "Validation error"),
    INVALID_REQUEST_PARAM_ERROR_CODE(10006, "Invalid request param"),
    INTERNAL_TIMEOUT_ERROR_CODE(10007, "Internal timeout"),
    METHOD_NOT_SUPPORTED_ERROR_CODE(10008, "Method not supported"),
    MISSING_REQUEST_HEADER_ERROR_CODE(10009, "Missing request header"),
    CARD_NOT_FOUND_CODE(10010, "Card not found"),
    HTTP_SERVICE_ERROR_CODE(10011, "Service error code"),
    HTTP_CLIENT_ERROR_CODE(10012, "Client error code"),

    ACCOUNT_STATUS_INVALID_CODE(10500, "Account status not ACTIVE"),
    AMS_REQUEST_INVALID_CODE(10510, "AMS request invalid"),
    ACCOUNT_NOT_FOUND_CODE(10520, "Account not found"),
    ACCOUNT_USER_INVALID_CODE(10530, "Account and User don't match"),

    WEBHOOK_REQUEST_INVALID(10590, "Webhook request invalid"),
    CLIENT_NOT_FOUND_CODE(10600, "Client with provided id not found"),
    ACCOUNT_VALIDATION_FAILED_CODE(10700, "Account validation failed"),
    CARD_EXPIRED_CODE(10800, "Card expired");

    final int code;
    final String message;

    Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
