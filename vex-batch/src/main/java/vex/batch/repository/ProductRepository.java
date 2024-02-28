package vex.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vex.batch.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
