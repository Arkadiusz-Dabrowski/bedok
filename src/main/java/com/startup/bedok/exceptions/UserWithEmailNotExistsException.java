package com.startup.bedok.exceptions;

public class UserWithEmailNotExistsException extends RuntimeException{

    public UserWithEmailNotExistsException(String email) {
        super(String.format("User with email: %s not exists", email));
    }
}
