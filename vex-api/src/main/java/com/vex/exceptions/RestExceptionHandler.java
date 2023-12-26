package com.vex.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger("RestExceptionHandler");

    @ExceptionHandler({ ServiceException.class })
    public ResponseEntity<Object> handleServiceException(ServiceException serviceException) {
        ServiceError serviceError = serviceException.getServiceError();
        String message = serviceError.getMessage();
        String description = serviceError.getDescription();
        String cause = serviceError.getCause();
        String appCode = serviceError.getAppCode();
        logger.error("\u001B[31mmessage={} | description={} | cause={} | appCode={}\u001B[0m", message, description, cause, appCode);
        return new ResponseEntity<>(serviceError, new HttpHeaders(), serviceException.getExceptionType().getHttpStatus());
    }


}
