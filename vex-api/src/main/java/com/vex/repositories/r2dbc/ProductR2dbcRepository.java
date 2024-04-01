package com.vex.repositories.r2dbc;


import com.vex.models.entities.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductR2dbcRepository extends ReactiveCrudRepository<Product, Integer> {
}
