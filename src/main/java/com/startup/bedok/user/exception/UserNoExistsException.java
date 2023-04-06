package com.startup.bedok.user.exception;

public class UserNoExistsException extends RuntimeException{

    public UserNoExistsException(String id) {
        super(String.format("User with id: %s not exists", id));
    }
}
