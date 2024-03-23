package com.vex.services.commons.business;

import com.vex.models.entities.Personal;
import com.vex.repositories.PersonalRepository;
import com.vex.services.commons.interfaces.PersonalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalServiceImpl implements PersonalService {
    private final PersonalRepository personalRepository;


    @Override
    public Mono<Personal> findByUsernameAndEnabled(String username, Boolean enabled) {
        return personalRepository.findByUsernameAndEnabled(username, enabled);
    }
}
