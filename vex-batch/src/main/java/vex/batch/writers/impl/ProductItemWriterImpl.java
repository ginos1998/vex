package vex.batch.writers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.entities.Product;
import vex.batch.repository.ProductRepository;
import vex.batch.writers.ProductItemWriter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductItemWriterImpl implements ProductItemWriter {
    private final ProductRepository productRepository;

    @Override
    public void write(@NonNull Chunk<? extends Product> chunk) {
        try {
            productRepository.saveAll(chunk.getItems());
        } catch (Exception e) {
            throw new BatchException(ExceptionType.PRODUCT_ITEM_WRITER_EXCEPTION, List.of(e.getMessage()), e);
        }
    }
}
