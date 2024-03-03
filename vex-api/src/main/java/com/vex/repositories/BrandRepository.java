package com.vex.repositories;

import com.vex.models.entities.Brand;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BrandRepository extends ReactiveCrudRepository<Brand, Integer> {
}
