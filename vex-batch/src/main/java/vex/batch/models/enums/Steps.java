package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Steps {

    IMPORT_PRODUCT_CSV("importProductsCsvStep"),
    CLEAN_UP("cleanUpJobStep");

    private final String value;

}
