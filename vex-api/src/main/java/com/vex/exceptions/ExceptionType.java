package com.vex.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    // sub-categories
    ERROR_GETTING_SUB_CATEGORIES("Error getting sub categories", "Error getting sub categories with categoryId=%s and subCategoryName=%s.", "1000", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GETTING_ALL_SUB_CATEGORIES("Error getting all sub categories", "Error getting all sub categories: %s", "1001", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_SAVING_SUB_CATEGORY("Error saving sub category", "Error saving sub category: %s", "1002", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPDATING_SUB_CATEGORY("Error updating sub category", "Error updating sub category id=%s values=%s", "1003", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DELETING_SUB_CATEGORY("Error deleting sub category", "Error deleting sub category id=%s", "1004", HttpStatus.INTERNAL_SERVER_ERROR),
    SUB_CATEGORY_NOT_FOUND("Sub category not found", "Sub category not found with id=%s", "1005", HttpStatus.NOT_FOUND),
    // categories
    ERROR_GETTING_CATEGORIES("Error getting categories", "Error getting categories with categoryId=%s, categoryName=%s.", "1010", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GETTING_ALL_CATEGORIES("Error getting all categories", "Error getting all categories: %s", "1011", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_SAVING_CATEGORY("Error saving category", "Error saving category: %s", "1012", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPDATING_CATEGORY("Error updating category", "Error updating category id=%s values=%s", "1013", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DELETING_CATEGORY("Error deleting category", "Error deleting category id=%s", "1014", HttpStatus.INTERNAL_SERVER_ERROR),
    CATEGORY_NOT_FOUND("Category not found", "Category not found with id=%s", "1015", HttpStatus.NOT_FOUND),

    // other
    ERROR_GETTING_PARAM_FROM_JWT("Error getting param from JWT token", "Error getting param from JWT token: %s", "1020", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT("Invalid input", "Invalid input: %s", "1021", HttpStatus.BAD_REQUEST),;

    private final String message;
    private final String description;
    private final String appCode;
    private final HttpStatus httpStatus;

    ExceptionType(String message, String description, String appCode, HttpStatus httpStatus) {
        this.message = message;
        this.description = description;
        this.appCode = appCode;
        this.httpStatus = httpStatus;
    }
}
