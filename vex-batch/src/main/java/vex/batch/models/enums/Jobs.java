package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Jobs {
    IMPORT_PRODUCT_CSV_JOB("importProductCsvJob"),
    MODIFY_PRODUCT_PRICE_JOB("modifyProductPriceJob"),
    SYNC_PRODUCT_PRICES_WITH_DOLLAR_JOB("syncProductPricesWithDollarJob"),;

    private final String name;
}
