package vex.batch.writers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.stereotype.Component;
import vex.batch.models.entities.Product;
import vex.batch.repository.ProductRepository;
import vex.batch.writers.ProductItemWriter;

@Component
@RequiredArgsConstructor
public class ProductItemWriterImpl implements ProductItemWriter {
    private final ProductRepository productRepository;

    @Override
    public void write(Chunk<? extends Product> chunk) {
        try {
            productRepository.saveAll(chunk.getItems());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
