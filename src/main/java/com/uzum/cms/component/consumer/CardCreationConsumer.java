package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.CardStatusCode;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.CardTerminalStateEvent;
import com.uzum.cms.dto.response.CardResponse;
import com.uzum.cms.exception.kafka.transients.TransientException;
import com.uzum.cms.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class CardCreationConsumer implements EventConsumer<CardEmissionEvent> {
    private final CardService cardService;
    private final CardEventProducer cardEventProducer;

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.CARD_CREATION_TOPIC, groupId = KafkaConstants.CARD_CREATION_TOPIC)
    public void listen(@Payload @Valid CardEmissionEvent event) {

        log.info("Card creation");

        CardResponse cardResponse = cardService.createCard(event);

        log.info("Card created with ID: {}", cardResponse.id());

        CardTerminalStateEvent cardTerminalStateEvent = new CardTerminalStateEvent(CardStatusCode.ACCOUNT_VALIDATION_FAILED, event,  cardResponse, null);

        cardEventProducer.publishForCardForTerminalStatus(cardTerminalStateEvent);
    }

    @DltHandler
    public void dltHandler(CardEmissionEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        CardTerminalStateEvent cardTerminalStateEvent = new CardTerminalStateEvent(CardStatusCode.ACCOUNT_VALIDATION_FAILED, event,  null, exceptionMessage);

        cardEventProducer.publishForCardForTerminalStatus(cardTerminalStateEvent);
    }
}
