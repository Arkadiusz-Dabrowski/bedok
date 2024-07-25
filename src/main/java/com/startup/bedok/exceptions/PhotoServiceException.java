package com.startup.bedok.exceptions;

import lombok.ToString;

@ToString
public class PhotoServiceException extends RuntimeException{

    public PhotoServiceException(String message) {
        super(message);
    }
}
