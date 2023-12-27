package com.vex.repositories.r2dbc;

import com.vex.models.entities.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryR2dbcRepository extends ReactiveCrudRepository<Category, Integer> {


}
