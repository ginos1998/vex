package vex.batch.processors.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import vex.batch.exceptions.BatchException;
import vex.batch.exceptions.ExceptionType;
import vex.batch.models.entities.Product;
import vex.batch.models.enums.DollarOperation;
import vex.batch.models.enums.DollarType;
import vex.batch.processors.SyncProductPricesWithDollarProcessor;
import vex.batch.services.DollarService;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SyncProductPricesWithDollarProcessorImpl implements SyncProductPricesWithDollarProcessor {

    private final DollarService dollarService;

    @Override
    public List<Product> process(@NonNull List<Product> items) {
       try {
           BigDecimal dollarValue = dollarService.getDollarValue(DollarType.BLUE, DollarOperation.PURCHASE);
           if (dollarValue == null) {
               log.error("Dollar value is null");
               return null;
           }
           log.info("Dollar {} value: {}", DollarType.BLUE.getName(), dollarValue);
           items.forEach(product -> product.setPrice(product.getDollarRate().multiply(dollarValue)));
           return items;
       } catch (Exception e) {
           throw new BatchException(ExceptionType.PRODUCT_DB_PROCESS_EXCEPTION_SYNCHRONIZING_WITH_DOLLAR_PRICE, List.of(e.getMessage()), e);
       }
    }
}
