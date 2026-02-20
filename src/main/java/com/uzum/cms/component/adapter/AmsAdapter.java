package com.uzum.cms.component.adapter;

import com.uzum.cms.configuration.property.AmsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Currency;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AmsAdapter {
    private final RestClient restClient;
    private final AmsProperties amsProperties;

    public void validateAccountByIdAndCurrency(final UUID accountId, final Currency currency) {
        String requestUrl = String.format("%s/validate-by-id-and-currency/%s/%s", amsProperties.getBaseUrl(), accountId, currency);

        restClient.get().uri(requestUrl).retrieve().toBodilessEntity();
    }

    public void validateByAccountIdAndUserId(final UUID accountId, final UUID userId) {
        String requestUrl = String.format("%s/validate-by-id-and-user/%s/%s", amsProperties.getBaseUrl(), accountId, userId);

        restClient.get().uri(requestUrl).retrieve().toBodilessEntity();
    }
}
