package com.vex.repositories.r2dbc;

import com.vex.models.entities.SubCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryR2dbcRepository extends ReactiveCrudRepository<SubCategory, Long> {

}
