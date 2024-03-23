package com.vex.services.commons.interfaces;

import com.vex.models.entities.Personal;
import reactor.core.publisher.Mono;

public interface PersonalService {
    Mono<Personal> findByUsernameAndEnabled(String username, Boolean enabled);
}
