package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Steps {

    IMPORT_PRODUCT_CSV("importProductsCsvStep"),
    MODIFY_PRODUCT_PRICE_STEP("modifyProductPriceStep"),
    CLEAN_UP("cleanUpJobStep");

    private final String value;

}
