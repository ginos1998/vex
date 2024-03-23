package com.vex.services.kafka.listeners;

import com.vex.models.entities.Personal;
import com.vex.repositories.PersonalRepository;
import com.vex.services.kafka.interfaces.KafkaListeners;
import com.vex.utils.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListenersImpl implements KafkaListeners {

    private final PersonalRepository personalRepository;
    @Override
    @KafkaListener(topics = KafkaTopics.NEW_USER, groupId = "vex-server-auth")
    public void listener(String username) {
        Personal personal = Personal.builder()
            .username(username)
            .enabled(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        personalRepository.create(personal)
            .doAfterTerminate(() -> log.info("personal {} saved successfully!", personal.getUsername()))
            .subscribe();
    }
}
