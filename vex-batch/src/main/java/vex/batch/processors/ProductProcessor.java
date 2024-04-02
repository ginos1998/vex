package vex.batch.processors;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.entities.Product;

import java.time.LocalDateTime;
import java.util.List;

public class ProductProcessor implements ItemProcessor<Product, Product> {

    @Override
    public Product process(@NonNull Product item) {
        try {
            item.setProductId(null);
            item.setCreatedAt(LocalDateTime.now());
            item.setUpdatedAt(LocalDateTime.now());
            return item;
        } catch (Exception e) {
            String error = String.format(ExceptionType.PRODUCT_CSV_PROCESSOR_EXCEPTION.getDescription(), item);
            List<String> errors = List.of(error, e.getMessage());
            throw new BatchException(ExceptionType.INVALID_INPUT, errors, e);
        }
    }
}
