package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.component.producer.WebhookProducer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.constant.enums.WebhookCode;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.WebhookEvent;
import com.uzum.cms.dto.response.ClientInfoResponse;
import com.uzum.cms.exception.http.HttpClientException;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.nontransiets.HttpRequestInvalidException;
import com.uzum.cms.exception.kafka.nontransiets.client.ClientNotFoundException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import com.uzum.cms.exception.kafka.transients.TransientException;
import com.uzum.cms.service.ClientIntegrationService;
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
public class CardUserValidationConsumer implements EventConsumer<CardEmissionEvent> {
    private final ClientIntegrationService clientIntegrationService;
    private final CardEventProducer cardEventProducer;
    private final WebhookProducer webhookProducer;

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.USER_VALIDATE_TOPIC, groupId = KafkaConstants.USER_VALIDATE_GROUP)
    public void listen(@Payload @Valid CardEmissionEvent event) {

        try {
            ClientInfoResponse clientInfoResponse = clientIntegrationService.fetchClientInfoById(event.userId());

            // TODO: What we should validate?

            cardEventProducer.publishForCardCreation(event);

        } catch (HttpServerException e) {
            throw new HttpServerUnavailableException(e);

        } catch (HttpClientException e) {

            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                throw new ClientNotFoundException(Error.ACCOUNT_NOT_FOUND_CODE);
            } else {
                throw new HttpRequestInvalidException(Error.AMS_REQUEST_INVALID_CODE, e);
            }

        }
    }

    @DltHandler
    public void dltHandler(CardEmissionEvent event, String exceptionMessage) {
        WebhookEvent webhookEvent = new WebhookEvent(WebhookCode.USER_VALIDATION_FAILED, exceptionMessage, null);
        webhookProducer.publishWebhookEvent(webhookEvent);
    }
}
