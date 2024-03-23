package com.vex.repositories.r2dbc;

import com.vex.models.entities.Company;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CompanyR2dbcRepository extends ReactiveCrudRepository<Company, Integer> {
    Mono<Company> findCompanyByCompanyIdAndActiveEqualsIgnoreCase(Integer companyId, String active);
}
