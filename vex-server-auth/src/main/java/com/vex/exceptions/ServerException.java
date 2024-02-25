package com.vex.exceptions;

import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;

@Getter
public class ServerException extends Exception {
    private final ExceptionType exceptionType;
    private final String description;
    private final String exceptionCause;

    public ServerException(Exception e, ExceptionType exceptionType, Object... args) {
        super(String.format(exceptionType.getMessage(), args), e);
        description = String.format(exceptionType.getDescription(), args);
        exceptionCause = ExceptionUtils.getRootCauseMessage(e);
        this.exceptionType = exceptionType;
    }

    public ServerError getServerError() {
        return ServerError.builder()
            .message(exceptionType.getMessage())
            .description(this.getDescription())
            .appCode(exceptionType.getAppCode())
            .cause(this.getExceptionCause())
            .build();
    }
}
