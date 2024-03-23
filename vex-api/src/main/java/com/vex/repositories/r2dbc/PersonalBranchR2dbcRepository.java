package com.vex.repositories.r2dbc;

import com.vex.models.entities.PersonalBranch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonalBranchR2dbcRepository extends ReactiveCrudRepository<PersonalBranch, Integer> {
}
