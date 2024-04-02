package vex.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import vex.batch.models.entities.Product;

import java.util.List;

public interface SyncProductPricesWithDollarProcessor extends ItemProcessor<List<Product>, List<Product>> {
}
