package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.component.producer.WebhookProducer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.AccountStatus;
import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.WebhookCode;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.WebhookEvent;
import com.uzum.cms.dto.response.AMSInfoResponse;
import com.uzum.cms.exception.http.HttpClientException;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.nontransiets.account.AccountNotActiveException;
import com.uzum.cms.exception.kafka.nontransiets.account.AccountNotFoundException;
import com.uzum.cms.exception.kafka.nontransiets.account.AccountUserNotMatchExcpetion;
import com.uzum.cms.exception.kafka.nontransiets.HttpRequestInvalidException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import com.uzum.cms.exception.kafka.transients.TransientException;
import com.uzum.cms.service.AmsIntegrationService;
import com.uzum.cms.service.WebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardAccountValidationConsumer implements EventConsumer<CardEmissionEvent> {
    private final AmsIntegrationService amsIntegrationService;
    private final CardEventProducer cardEventProducer;
    private final WebhookProducer webhookProducer;

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.ACCOUNT_VALIDATE_TOPIC, groupId = KafkaConstants.ACCOUNT_VALIDATE_TOPIC)
    public void listen(@Payload @Valid CardEmissionEvent event) {

        try {
            AMSInfoResponse amsInfoResponse = amsIntegrationService.fetchAccountInfoById(event.accountId());

            if (!amsInfoResponse.status().equals(AccountStatus.ACTIVE)) {
                throw new AccountNotActiveException(Error.ACCOUNT_STATUS_INVALID_CODE);
            }

            if (!amsInfoResponse.userId().equals(event.userId())) {
                throw new AccountUserNotMatchExcpetion(Error.ACCOUNT_USER_INVALID_CODE);
            }

            cardEventProducer.publishForCardCreation(event);

        } catch (HttpServerException e) {
            throw new HttpServerUnavailableException(e);

        } catch (HttpClientException e) {
            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                throw new AccountNotFoundException(Error.ACCOUNT_NOT_FOUND_CODE);
            } else {
                throw new HttpRequestInvalidException(Error.AMS_REQUEST_INVALID_CODE, e);
            }
        }

    }

    @DltHandler
    public void dltHandler(CardEmissionEvent event, String exceptionMessage) {
        WebhookEvent webhookEvent = new WebhookEvent(WebhookCode.ACCOUNT_VALIDATION_FAILED, exceptionMessage, null);

        webhookProducer.publishWebhookEvent(webhookEvent);
    }
}
