package com.uzum.cms.service;

import com.uzum.cms.dto.event.WebhookEvent;

public interface WebhookService {

    void send(final WebhookEvent event);
}
