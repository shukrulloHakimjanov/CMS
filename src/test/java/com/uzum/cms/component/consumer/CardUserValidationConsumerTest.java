package com.uzum.cms.component.consumer;

import com.uzum.cms.component.adapter.ClientServiceAdapter;
import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.constant.enums.CardNetworkType;
import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.CardTerminalStateEvent;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardUserValidationConsumerTest {

    @Mock
    private ClientServiceAdapter clientServiceAdapter;

    @Mock
    private CardEventProducer cardEventProducer;

    @InjectMocks
    private CardUserValidationConsumer userValidationConsumer;


    private CardEmissionEvent event;

    @BeforeEach
    void setUp() {
        event = new CardEmissionEvent(UUID.randomUUID(), UUID.randomUUID(), "John Snow", CardNetworkType.VISA, CardType.CORPORATE, "encrypted-pin-value");
    }


    @Test
    void shouldPublishCardCreation_whenClientExists() {
        doNothing().when(clientServiceAdapter).validateById(event.userId());

        userValidationConsumer.listen(event);

        verify(cardEventProducer).publishForCardCreation(event);
    }


    @Test
    void shouldSendWebhook_whenServerUnavailable_andRetriesExhausted() {
        // given
        HttpServerException cause = new HttpServerException("Server down", HttpStatus.INTERNAL_SERVER_ERROR);
        HttpServerUnavailableException wrapped =
            new HttpServerUnavailableException(cause);

        // when
        userValidationConsumer.dltHandler(event, wrapped.getMessage());

        // then
        ArgumentCaptor<CardTerminalStateEvent> captor = ArgumentCaptor.forClass(CardTerminalStateEvent.class);

        verify(cardEventProducer).publishForCardForTerminalStatus(captor.capture());
    }

}