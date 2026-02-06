package com.uzum.cms.component.consumer;

import com.uzum.cms.component.producer.CardEventProducer;
import com.uzum.cms.component.producer.WebhookProducer;
import com.uzum.cms.constant.enums.CardNetworkType;
import com.uzum.cms.constant.enums.CardType;
import com.uzum.cms.constant.enums.WebhookCode;
import com.uzum.cms.dto.event.CardEmissionEvent;
import com.uzum.cms.dto.event.WebhookEvent;
import com.uzum.cms.dto.response.ClientInfoResponse;
import com.uzum.cms.exception.http.HttpServerException;
import com.uzum.cms.exception.kafka.transients.HttpServerUnavailableException;
import com.uzum.cms.service.ClientIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardUserValidationConsumerTest {
    @Mock
    private ClientIntegrationService clientIntegrationService;

    @Mock
    private CardEventProducer cardEventProducer;

    @Mock
    private WebhookProducer webhookProducer;

    @InjectMocks
    private CardUserValidationConsumer userValidationConsumer;


    private CardEmissionEvent event;

    @BeforeEach
    void setUp() {
        event = new CardEmissionEvent(UUID.randomUUID(), UUID.randomUUID(), "John Snow", CardNetworkType.VISA, CardType.CORPORATE, "encrypted-pin-value");
    }


    @Test
    void shouldPublishCardCreation_whenClientExists() {
        ClientInfoResponse response = new ClientInfoResponse(UUID.randomUUID(), "Name", "LastName", true, OffsetDateTime.now());

        when(clientIntegrationService.fetchClientInfoById(event.userId())).thenReturn(response);

        userValidationConsumer.listen(event);

        verify(cardEventProducer).publishForCardCreation(event);
        verifyNoInteractions(webhookProducer);
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
        ArgumentCaptor<WebhookEvent> captor = ArgumentCaptor.forClass(WebhookEvent.class);

        verify(webhookProducer).publishWebhookEvent(captor.capture());

        WebhookEvent webhookEvent = captor.getValue();
        assertEquals(WebhookCode.USER_VALIDATION_FAILED, webhookEvent.code());
    }

}