package vex.batch.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import vex.batch.models.entities.Product;

import java.util.Date;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(@NonNull Product item) {
        item.setProductId(null);
        item.setCreatedAt(new Date());
        item.setUpdatedAt(new Date());
        return item;
    }
}
