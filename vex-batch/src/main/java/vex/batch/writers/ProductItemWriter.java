package vex.batch.writers;

import org.springframework.batch.item.ItemWriter;
import vex.batch.models.entities.Product;

public interface ProductItemWriter extends ItemWriter<Product> {

}
