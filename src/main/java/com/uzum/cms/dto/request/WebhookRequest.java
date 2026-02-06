package com.uzum.cms.dto.request;

import com.uzum.cms.dto.response.CardResponse;

public record WebhookRequest(Integer code, String message, String details, CardResponse card) {
}
