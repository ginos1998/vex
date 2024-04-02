package vex.batch.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class BatchExceptionHandler {
    @ExceptionHandler(BatchException.class)
    public void handleException(BatchException batchException) {
        ServiceError serviceError = batchException.getServiceError();
        String message = serviceError.getMessage();
        String description = serviceError.getDescription();
        Integer code = serviceError.getCode();
        log.error("\u001B[31mmessage={} | description={} | appCode={}\u001B[0m", message, description, code);
    }
}

