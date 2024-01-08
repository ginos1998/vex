package com.vex.repositories.r2dbc;

import com.vex.models.entities.Personal;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonalR2dbcRepository extends ReactiveCrudRepository<Personal, Integer> {

}
