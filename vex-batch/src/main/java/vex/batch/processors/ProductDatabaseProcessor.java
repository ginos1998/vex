package vex.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import vex.batch.models.entities.Product;

import java.util.List;

public interface ProductDatabaseProcessor extends ItemProcessor<List<Product>, List<Product>> {
    void setPercentage(Integer percentage);
}
