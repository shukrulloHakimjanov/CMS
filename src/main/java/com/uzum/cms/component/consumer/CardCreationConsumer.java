package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.component.producer.WebhookProducer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.WebhookCode;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.WebhookEvent;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.exception.kafka.transients.TransientException;
import com.uzum.cms.service.CardService;
import com.uzum.cms.service.WebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CardCreationConsumer implements EventConsumer<CardEmissionEvent> {
    private final CardService cardService;
    private final WebhookProducer webhookProducer;

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.CARD_CREATION_TOPIC, groupId = KafkaConstants.CARD_CREATION_TOPIC)
    public void listen(@Payload @Valid CardEmissionEvent event) {
        CardResponse cardResponse = cardService.createCard(event);

        WebhookEvent webhookEvent = new WebhookEvent(WebhookCode.SUCCESS, null, cardResponse);

        webhookProducer.publishWebhookEvent(webhookEvent);
    }

    @DltHandler
    public void dltHandler(CardEmissionEvent event, String exceptionMessage) {
        WebhookEvent webhookEvent = new WebhookEvent(WebhookCode.CARD_CREATION_FAILED, exceptionMessage, null);
        webhookProducer.publishWebhookEvent(webhookEvent);
    }
}
