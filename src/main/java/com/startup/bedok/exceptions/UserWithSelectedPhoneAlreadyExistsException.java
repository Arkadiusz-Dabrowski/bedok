package com.startup.bedok.exceptions;

public class UserWithSelectedPhoneAlreadyExistsException extends RuntimeException {

    public UserWithSelectedPhoneAlreadyExistsException(String message) {
        super(String.format("User with phone: %s already exists", message));
    }
}
