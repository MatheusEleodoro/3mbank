package com.threembank.transaction.infrastructure.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public void sendMessage(String topic, TransactionEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
