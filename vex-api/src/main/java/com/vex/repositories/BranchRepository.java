package com.vex.repositories;

import com.vex.models.entities.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BranchRepository extends ReactiveCrudRepository<Branch, Integer> {
}
