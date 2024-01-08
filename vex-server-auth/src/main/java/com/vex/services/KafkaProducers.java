package com.vex.services;

import com.vex.enums.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducers {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendNewUserMessage(String message) {
        log.info("sending message='{}' to topic='{}'", message, KafkaTopics.NEW_USER.name());
        kafkaTemplate.send(KafkaTopics.NEW_USER.name(), message);
    }
}
