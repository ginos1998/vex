package vex.batch.writers;

import org.springframework.batch.item.ItemWriter;
import vex.batch.models.entities.Product;

import java.util.List;

public interface SyncProductPricesWithDollarWritter extends ItemWriter<List<Product>> {
}
