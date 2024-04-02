package vex.batch.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

    // general
    INVALID_INPUT(1000, "Invalid input", "The input is invalid. Details: %s"),

    // products
    PRODUCT_DATABASE_WRITER_EXCEPTION(1100, "An error occurred while writing products to the database", "An error occurred while writing products to the database. Details: %s"),
    PRODUCT_ITEM_WRITER_EXCEPTION(1101, "An error occurred while writing products", "An error occurred while writing products. Details: %s"),
    PRODUCT_DATABASE_READER_EXCEPTION(1102, "An error occurred while reading products from the database", "An error occurred while reading products from the database with branch id = %s"),
    PRODUCT_CSV_READER_EXCEPTION(1103, "An error occurred while reading products from the CSV file", "An error occurred while reading products from the CSV file. Details: %s"),
    PRODUCT_CSV_PROCESSOR_EXCEPTION(1104, "An error occurred while processing a product from the CSV file", "An error occurred while processing a product from the CSV file. Product: %s"),
    PRODUCT_DB_PROCESS_EXCEPTION(1105, "An error occurred while processing a product from the database", "An error occurred while processing a product from the database. Product: %s"),

    ;
    private final Integer code;
    private final String message;
    private final String description;
}
