package com.vex.repositories;

import com.vex.models.entities.Province;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProvinceRepository extends ReactiveCrudRepository<Province, Integer> {
}
