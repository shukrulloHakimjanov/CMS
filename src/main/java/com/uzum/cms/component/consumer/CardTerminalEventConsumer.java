package com.uzum.cms.component.consumer;

import com.uzum.cms.component.EventConsumer;
import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.constant.enums.Error;
import com.uzum.cms.dto.event.CardTerminalStateEvent;
import com.uzum.cms.exception.http.HttpClientException;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.nontransiets.HttpRequestInvalidException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import com.uzum.cms.exception.kafka.transients.TransientException;
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
public class CardTerminalEventConsumer implements EventConsumer<CardTerminalStateEvent> {

    @RetryableTopic(attempts = "5", backOff = @BackOff(delay = 5000), include = {TransientException.class}, numPartitions = "3", replicationFactor = "1")
    @KafkaListener(topics = KafkaConstants.TERMINAL_STATE_TOPIC, groupId = KafkaConstants.TERMINAL_STATE_GROUP)
    public void listen(@Payload @Valid CardTerminalStateEvent event) {

        log.info("Card in terminal state: {}", event);
    }

    @DltHandler
    public void dltHandler(CardTerminalStateEvent event, @Header(KafkaHeaders.EXCEPTION_MESSAGE) String exceptionMessage) {
    }
}
