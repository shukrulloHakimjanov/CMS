package com.uzum.cms.dto.event;

import com.uzum.cms.constant.enums.WebhookCode;
import com.uzum.cms.dto.response.CardResponse;

public record WebhookEvent(WebhookCode code, String errorMessage, CardResponse card) {
}
