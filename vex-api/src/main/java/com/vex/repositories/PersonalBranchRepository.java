package com.vex.repositories;

import com.vex.models.entities.PersonalBranch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PersonalBranchRepository extends ReactiveCrudRepository<PersonalBranch, Integer> {
}
