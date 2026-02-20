package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.component.adapter.ClientServiceAdapter;
import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.CardStatusCode;
import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.CardTerminalStateEvent;
import com.uzum.cms.exception.http.HttpClientException;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.nontransiets.HttpRequestInvalidException;
import com.uzum.cms.exception.kafka.nontransiets.client.ClientNotFoundException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import com.uzum.cms.exception.kafka.transients.TransientException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
public class CardUserValidationConsumer implements EventConsumer<CardEmissionEvent> {
    private final ClientServiceAdapter clientServiceAdapter;
    private final CardEventProducer cardEventProducer;

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.USER_VALIDATE_TOPIC, groupId = KafkaConstants.USER_VALIDATE_GROUP)
    public void listen(@Payload @Valid CardEmissionEvent event) {

        log.info("Validating client");

        try {
            clientServiceAdapter.validateById(event.userId());

            cardEventProducer.publishForCardCreation(event);

            log.info("Client validation success");

        } catch (HttpServerException e) {

            log.info("Client validation failed with Server exception: {}", e.getMessage());

            throw new HttpServerUnavailableException(e);

        } catch (HttpClientException e) {

            log.info("Client validation failed with Client exception: {}", e.getMessage());

            if (e.getStatus().equals(HttpStatus.NOT_FOUND)) {
                throw new ClientNotFoundException(Error.ACCOUNT_NOT_FOUND_CODE);
            } else {
                throw new HttpRequestInvalidException(Error.AMS_REQUEST_INVALID_CODE, e);
            }

        }
    }

    @DltHandler
    public void dltHandler(CardEmissionEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
        CardTerminalStateEvent cardTerminalStateEvent = new CardTerminalStateEvent(CardStatusCode.ACCOUNT_VALIDATION_FAILED, event,  null, exceptionMessage);

        cardEventProducer.publishForCardForTerminalStatus(cardTerminalStateEvent);
    }
}
