package vex.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vex.batch.models.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByBranchIdAndEnabled(Integer branchId, String enabled);

    @Query(" SELECT p FROM Product p " +
        " INNER JOIN Branch b on p.branchId = b.branchId " +
        " INNER JOIN BranchParameters bp on b.branchId = bp.branch.branchId " +
        " INNER JOIN Parameters pa on bp.parameters.parameterId = pa.parameterId " +
        " WHERE b.active = 'Y' AND bp.active = 'Y' AND p.enabled = 'Y' " +
        " AND pa.parameterId = 1 ") // 1 is the parameterId for sync price with dollar
    List<Product> findProductToSyncPriceWithDollarByBranch();
}
