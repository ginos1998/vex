package vex.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vex.batch.models.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByBranchIdAndEnabled(Integer branchId, String enabled);
}
