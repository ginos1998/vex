package vex.batch.processors.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.entities.Product;
import vex.batch.processors.ProductDatabaseProcessor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class ProductDatabaseProcessorImpl implements ProductDatabaseProcessor {
    private Integer percentage;

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    private Integer getPercentage() {
        return percentage == null ? 100 : percentage;
    }

    @Override
    public List<Product> process(@NonNull List<Product> items) {
        try {
            for (Product item : items) {
                BigDecimal rate = BigDecimal.valueOf(1).add(BigDecimal.valueOf(getPercentage()).divide(BigDecimal.valueOf(100)));
                BigDecimal newPrice = item.getPrice().multiply(rate);
                item.setPrice(newPrice);
                item.setUpdatedAt(LocalDateTime.now());
            }
            return items;
        } catch (Exception e) {
            String error = String.format(ExceptionType.PRODUCT_DB_PROCESS_EXCEPTION.getDescription(), items);
            List<String> errors = List.of(error, e.getMessage());
            throw new BatchException(ExceptionType.PRODUCT_DB_PROCESS_EXCEPTION, errors, e);
        }
    }
}
