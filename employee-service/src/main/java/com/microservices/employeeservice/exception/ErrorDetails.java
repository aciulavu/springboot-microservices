package com.microservices.employeeservice.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDetails {

    private LocalDateTime timpstamp;
    private String message;
    private String path;
    private String errorCode;
}
