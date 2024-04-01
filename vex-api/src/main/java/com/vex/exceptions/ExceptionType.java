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
    INVALID_INPUT("Invalid input", "Invalid input: %s", "1021", HttpStatus.BAD_REQUEST),

    // company
    ERROR_SAVING_COMPANY("Error saving company", "Error saving company: %s", "1030", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPDATING_COMPANY("Error updating company", "Error updating company: %s", "1031", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DELETING_COMPANY("Error deleting company", "Error deleting company: %s", "1032", HttpStatus.INTERNAL_SERVER_ERROR),
    COMPANY_NOT_FOUND("Company not found", "Company not found with id=%s", "1033", HttpStatus.NOT_FOUND),
    ERROR_GETTING_COMPANY("Error getting company", "Error getting company: %s", "1034", HttpStatus.INTERNAL_SERVER_ERROR),

    // branch
    ERROR_SAVING_BRANCH("Error saving branch", "Error saving branch: %s", "1040", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPDATING_BRANCH("Error updating branch", "Error updating branch: %s", "1041", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DELETING_BRANCH("Error deleting branch", "Error deleting branch: %s", "1042", HttpStatus.INTERNAL_SERVER_ERROR),
    BRANCH_NOT_FOUND("Branch not found", "Branch not found with id=%s", "1043", HttpStatus.NOT_FOUND),
    ERROR_GETTING_BRANCH("Error getting branch", "Error getting branch: %s", "1044", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GETTING_BRANCHES_BY_COMPANY("Error getting branches by company", "Error getting branches by company: %s", "1045", HttpStatus.INTERNAL_SERVER_ERROR),
    NO_BRANCHES_FOUND("No branches found", "No branches found for company id=%s", "1046", HttpStatus.NOT_FOUND),

    // personal
    PERSONAL_NOT_FOUND("Personal not found", "Personal not found: %s", "1100", HttpStatus.NOT_FOUND),

    // product
    ERROR_CREATING_PRODUCT("Error creating product", "Error creating product: %s", "1200", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPDATING_PRODUCT("Error updating product", "Error updating product: %s", "1201", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DELETING_PRODUCT("Error deleting product", "Error deleting product: %s", "1202", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GETTING_PRODUCTS_BY_BRANCH_WITH_FILTER("Error getting products by branch with filter", "Error getting products by branch with filter: %s", "1203", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GETTING_PRODUCT("Error getting product", "Error getting product: %s", "1204", HttpStatus.INTERNAL_SERVER_ERROR),
    PRODUCT_NOT_FOUND("Product not found", "Product not found with id=%s", "1205", HttpStatus.NOT_FOUND),;

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
