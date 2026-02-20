package com.uzum.cms.component.adapter;

import com.uzum.cms.configuration.property.ClientServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClientServiceAdapter {
    private final RestClient restClient;
    private final ClientServiceProperties clientServiceProperties;

    public void validateById(final UUID userId) {
        String requestUrl = String.format("%s/validate/%s", clientServiceProperties.getBaseUrl(), userId);

        restClient.get().uri(requestUrl).retrieve().toBodilessEntity();
    }
}
