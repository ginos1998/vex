package vex.batch.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Jobs {
    IMPORT_PRODUCT_CSV_JOB("importProductCsvJob");

    private String name;
}
