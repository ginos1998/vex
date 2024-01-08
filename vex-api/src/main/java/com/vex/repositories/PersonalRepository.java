package com.vex.repositories;

import com.vex.models.entities.Personal;
import reactor.core.publisher.Mono;

public interface PersonalRepository {
    Mono<Personal> create(Personal personal);
}
