package vex.batch.readers;

import org.springframework.batch.item.ItemReader;
import vex.batch.models.entities.Product;

import java.util.List;

public interface SyncProductPricesWithDollarReader extends ItemReader<List<Product>> {
    void setRead(boolean read);
}
