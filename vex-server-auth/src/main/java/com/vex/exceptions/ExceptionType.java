package com.vex.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionType {

    INTERNAL_SERVER_ERROR("Internal server error", "Internal server error: %s", "1000", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_GENERATING_KEY_PAIR("Error generating key pair", "Error generating key pair: %s", "1001", HttpStatus.INTERNAL_SERVER_ERROR),;

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
