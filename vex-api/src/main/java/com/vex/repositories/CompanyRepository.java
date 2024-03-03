package com.vex.repositories;

import com.vex.models.entities.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CompanyRepository extends ReactiveCrudRepository<Company, Integer> {
}
