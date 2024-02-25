package com.vex.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Getter
public class ServiceException extends Exception {

    private final ExceptionType exceptionType;
    private final String description;
    private final String exceptionCause;

    public ServiceException(Exception e, ExceptionType exceptionType, Object... args) {
        super(String.format(exceptionType.getMessage(), args), e);
        description = String.format(exceptionType.getDescription(), args);
        exceptionCause = ExceptionUtils.getRootCauseMessage(e);
        this.exceptionType = exceptionType;

    }

    public ServiceException(Throwable e, ExceptionType exceptionType, Object... args) {
        super(String.format(exceptionType.getMessage(), args), e);
        description = String.format(exceptionType.getDescription(), args);
        exceptionCause = ExceptionUtils.getRootCauseMessage(e);
        this.exceptionType = exceptionType;

    }

    public ServiceException(ExceptionType exceptionType, Object... args) {
        super(String.format(exceptionType.getMessage(), args));
        description = String.format(exceptionType.getDescription(), args);
        exceptionCause = null;
        this.exceptionType = exceptionType;
    }

    public ServiceError getServiceError() {
        return new ServiceError(
                exceptionType.getMessage(),
                this.getDescription(),
                exceptionType.getAppCode(),
                this.getExceptionCause()
        );
    }
}
