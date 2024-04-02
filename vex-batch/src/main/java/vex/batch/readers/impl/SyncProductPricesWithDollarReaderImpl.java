package vex.batch.readers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.entities.Product;
import vex.batch.readers.SyncProductPricesWithDollarReader;
import vex.batch.repository.ProductRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SyncProductPricesWithDollarReaderImpl implements SyncProductPricesWithDollarReader {

    private final ProductRepository productRepository;
    private boolean read = false;

    @Override
    public List<Product> read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            if (!read) {
                read = true;
                List<Product> products = productRepository.findProductToSyncPriceWithDollarByBranch();
                log.info("Job found {} products to sync price with dollar", products.size());
                return products;
            }
            return null;
        } catch (Exception e) {
            String error = String.format(ExceptionType.PRODUCT_DB_READER_EXCEPTION_READING_PRODUCTS_TO_SYNC_PRICE_WITH_DOLLAR.getDescription(), e.getMessage());
            throw new BatchException(ExceptionType.PRODUCT_DB_READER_EXCEPTION_READING_PRODUCTS_TO_SYNC_PRICE_WITH_DOLLAR, List.of(error, e.getMessage()), e);
        }
    }

    @Override
    public void setRead(boolean read) {
        this.read = read;
    }

}
