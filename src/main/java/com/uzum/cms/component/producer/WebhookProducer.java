package com.uzum.cms.component.producer;

import com.uzum.cms.constant.KafkaConstants;
import com.uzum.cms.dto.event.WebhookEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebhookProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishWebhookEvent(final WebhookEvent event) {
        kafkaTemplate.send(KafkaConstants.WEBHOOK_TOPIC, event);
    }
}
