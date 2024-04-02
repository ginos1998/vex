package vex.batch.writers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.entities.Product;
import vex.batch.repository.ProductRepository;
import vex.batch.writers.ProductDatabaseWriter;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDatabaseWriterImpl implements ProductDatabaseWriter {

    private final ProductRepository productRepository;

    @Override
    public void write(@NonNull Chunk<? extends List<Product>> chunk) {
        try {
            List<Product> products = chunk.getItems().get(0);
            productRepository.saveAll(products);
        } catch (Exception e) {
            throw new BatchException(ExceptionType.PRODUCT_DATABASE_WRITER_EXCEPTION, List.of(e.getMessage()), e);
        }
    }

}
