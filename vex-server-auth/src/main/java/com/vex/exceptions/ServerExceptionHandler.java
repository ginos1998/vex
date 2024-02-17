package com.vex.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ServerExceptionHandler {

    @ExceptionHandler({ServerException.class})
    public ResponseEntity<Object> handleServerError(ServerException serverException) {
        ServerError serverError = serverException.getServerError();
        String message = serverError.getMessage();
        String description = serverError.getDescription();
        String cause = serverError.getCause();
        String appCode = serverError.getAppCode();
        log.error("\u001B[31mmessage={} | description={} | cause={} | appCode={}\u001B[0m", message, description, cause, appCode);
        return new ResponseEntity<>(serverError, new HttpHeaders(), serverException.getExceptionType().getHttpStatus());
    }
}
