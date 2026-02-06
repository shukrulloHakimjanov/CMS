package com.uzum.cms.service.impl;

import com.uzum.cms.dto.event.WebhookEvent;
import com.uzum.cms.dto.request.WebhookRequest;
import com.uzum.cms.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {
    private final RestClient webhookRestClient;


    public void send(final WebhookEvent event) {
        WebhookRequest request = new WebhookRequest(
            event.code().getCode(),
            event.code().getMessage(),
            event.errorMessage(),
            event.card()
        );

        webhookRestClient.post().body(request);
    }
}
