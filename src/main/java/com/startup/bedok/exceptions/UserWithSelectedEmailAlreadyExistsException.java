package com.startup.bedok.exceptions;

import lombok.ToString;


@ToString
public class UserWithSelectedEmailAlreadyExistsException extends RuntimeException {

    public UserWithSelectedEmailAlreadyExistsException(String message) {
        super(String.format("User with email: %s already exists", message));
    }
}
