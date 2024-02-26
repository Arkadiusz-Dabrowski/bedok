package com.startup.bedok.exceptions;

public class HostNoExistsException extends RuntimeException{

    public HostNoExistsException(String id) {
        super(String.format("Host with id: %s not exists", id));
    }
}
