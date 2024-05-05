package com.startup.bedok.exceptions;

import lombok.ToString;

@ToString
public class AuthenticationException extends RuntimeException{

    public AuthenticationException() {
        super("Incorrect login data");
    }
}
