package com.startup.bedok.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
public class CustomException {
    private final HttpStatus status;
    private final String message;
    private List<String> errors;

    public CustomException(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public CustomException(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }

    public CustomException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}
