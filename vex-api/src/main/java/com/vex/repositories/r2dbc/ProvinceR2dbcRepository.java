package com.vex.repositories.r2dbc;

import com.vex.models.entities.Province;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProvinceR2dbcRepository extends ReactiveCrudRepository<Province, Integer> {
}
