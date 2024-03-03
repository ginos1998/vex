package com.vex.repositories;

import com.vex.models.entities.Locality;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LocalityRepository extends ReactiveCrudRepository<Locality, Integer> {
}
