package vex.batch.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServiceError {
    private String message;
    private String description;
    private Integer code;
}