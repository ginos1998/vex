package com.vex.repositories.r2dbc;

import com.vex.models.entities.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchR2dbcRepository extends ReactiveCrudRepository<Branch, Integer> {

    Flux<Branch> findAllByCompanyIdAndActiveEqualsIgnoreCase(Integer companyId, String active);
}
