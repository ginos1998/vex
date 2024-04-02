package vex.batch.readers;

import org.springframework.batch.item.ItemReader;
import vex.batch.models.entities.Product;

import java.util.List;

public interface ProductDatabaseReader extends ItemReader<List<Product>> {
    void setBranchId(Integer branchId);
}
