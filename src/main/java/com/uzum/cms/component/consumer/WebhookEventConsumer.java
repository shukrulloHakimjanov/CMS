package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.dto.event.WebhookEvent;
import com.uzum.cms.exception.http.HttpClientException;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.nontransiets.HttpRequestInvalidException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import com.uzum.cms.exception.kafka.transients.TransientException;
import com.uzum.cms.service.WebhookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebhookEventConsumer implements EventConsumer<WebhookEvent> {
    private final WebhookService webhookService;

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.WEBHOOK_TOPIC, groupId = KafkaConstants.WEBHOOK_GROUP)
    public void listen(WebhookEvent event) {

        try {
            webhookService.send(event);

        } catch (HttpServerException e) {
            throw new HttpServerUnavailableException(e);

        } catch (HttpClientException e) {
            throw new HttpRequestInvalidException(Error.WEBHOOK_REQUEST_INVALID, e);
        }
    }

    @DltHandler
    public void dltHandler(WebhookEvent event, String exceptionMessage) {
        log.error("Webhook event: {}, failed with error: {}", event, exceptionMessage);
    }
}
