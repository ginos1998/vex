package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Jobs {
    IMPORT_PRODUCT_CSV_JOB("importProductCsvJob");

    private final String name;
}
