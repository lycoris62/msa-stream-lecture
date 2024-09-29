package com.kafka.producer;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String key, String message) {

        kafkaTemplate.send(topic, key, message);
        kafkaTemplate.send(new Message<String>() {
            @Override
            public String getPayload() {
                return "message payload";
            }

            @Override
            public MessageHeaders getHeaders() {
                return new MessageHeaders(Map.of(
                    "test-key", "test-value",
                    KafkaHeaders.TOPIC, topic,
                    KafkaHeaders.KEY, key
                ));
            }
        });
    }
}
