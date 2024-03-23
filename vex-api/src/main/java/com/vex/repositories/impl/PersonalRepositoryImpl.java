package com.vex.repositories.impl;

import com.vex.models.entities.Personal;
import com.vex.repositories.PersonalRepository;
import com.vex.repositories.r2dbc.PersonalR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalRepositoryImpl implements PersonalRepository {

    private final PersonalR2dbcRepository personalR2dbcRepository;

    @Override
    public Mono<Personal> create(Personal personal) {
        return personalR2dbcRepository.save(personal);
    }

    @Override
    public Mono<Personal> findByUsernameAndEnabled(String username,Boolean enabled) {
        return personalR2dbcRepository.findByUsernameAndEnabled(username, enabled);
    }
}
