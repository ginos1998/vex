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
import vex.batch.readers.ProductDatabaseReader;
import vex.batch.repository.ProductRepository;
import vex.batch.utils.Constants;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDatabaseReaderImpl implements ProductDatabaseReader {

    private final ProductRepository productRepository;
    private boolean read = false;
    private Integer branchId;

    @Override
    public List<Product> read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        try {
            if (!read) {
                log.info("Reading products from database");
                read = true;
                return productRepository.findByBranchIdAndEnabled(branchId, Constants.STR_YES);
            }
            return null;
        } catch (Exception e) {
            String error = String.format(ExceptionType.PRODUCT_DATABASE_READER_EXCEPTION.getDescription(), branchId);
            List<String> errors = List.of(error, e.getMessage());
            throw new BatchException(ExceptionType.PRODUCT_DATABASE_READER_EXCEPTION, errors, e);
        }
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}
