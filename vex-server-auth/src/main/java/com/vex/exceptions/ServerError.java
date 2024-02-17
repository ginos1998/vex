package com.vex.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ServerError {
    private String message;
    private String description;
    private String appCode;
    private String cause;
}
