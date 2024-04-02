package vex.batch.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class BatchException extends RuntimeException {
    private final ExceptionType exceptionType;
    private final String description;

    public BatchException(ExceptionType exceptionType, List<String> errors, Exception e) {
        super(String.format(exceptionType.getMessage(), errors), e);
        description = String.format(exceptionType.getDescription(), errors);
        this.exceptionType = exceptionType;
    }

    public ServiceError getServiceError() {
        return new ServiceError(
            exceptionType.getMessage(),
            this.getDescription(),
            exceptionType.getCode()
        );
    }
}
