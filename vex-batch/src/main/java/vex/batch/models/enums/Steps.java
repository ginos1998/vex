package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Steps {

    IMPORT_PRODUCT_CSV("importProductsCsvStep"),
    MODIFY_PRODUCT_PRICE_STEP("modifyProductPriceStep"),
    FIND_PRODUCT_TO_SYNC_PRICE_WITH_DOLLAR_STEP("findProductToSyncPriceWithDollarStep"),
    CLEAN_UP("cleanUpJobStep");

    private final String value;

}
