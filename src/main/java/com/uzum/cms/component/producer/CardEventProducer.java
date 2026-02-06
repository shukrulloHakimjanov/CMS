package com.uzum.cms.component.producer;

import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.dto.event.CardEmissionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishForUserValidation(final CardEmissionEvent event) {
        kafkaTemplate.send(KafkaConstants.USER_VALIDATE_TOPIC, event);
    }

    public void publishForAccountValidation(final CardEmissionEvent event) {
        kafkaTemplate.send(KafkaConstants.ACCOUNT_VALIDATE_TOPIC, event);
    }

    public void publishForCardCreation(final CardEmissionEvent event) {
        kafkaTemplate.send(KafkaConstants.CARD_CREATION_TOPIC, event);
    }
}
