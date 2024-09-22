package com.startup.bedok.exceptions;

import lombok.ToString;

@ToString
public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException() {
        super("password is incorrect");
    }
}
