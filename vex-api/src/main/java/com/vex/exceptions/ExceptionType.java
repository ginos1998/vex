package com.vex.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    ERROR_GETTING_SUB_CATEGORIES("Error getting sub categories", "Error getting sub categories with categoryId=%s and subCategoryName=%s.", "1000", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GETTING_ALL_SUB_CATEGORIES("Error getting all sub categories", "Error getting all sub categories: %s", "1001", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_SAVING_SUB_CATEGORY("Error saving sub category", "Error saving sub category: %s", "1002", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPDATING_SUB_CATEGORY("Error updating sub category", "Error updating sub category id=%s values=%s", "1003", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DELETING_SUB_CATEGORY("Error deleting sub category", "Error deleting sub category id=%s", "1004", HttpStatus.INTERNAL_SERVER_ERROR),;

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
