package com.startup.bedok.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String id) {
        super(String.format("User with id: %s not exists", id));
    }

    public UserNotFoundException(UUID id) {
        super(String.format("User with id: %s not exists", id.toString()));
    }

}
