package vex.batch.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Reader {
    PRODUCT_ITEM_READER("productItemReader");

    private final String name;
}
