package com.startup.bedok.exceptions;

public class InvalidDateRangeException extends RuntimeException{

    public InvalidDateRangeException() {
        super("DateFrom must not be after DateTo");
    }
}
