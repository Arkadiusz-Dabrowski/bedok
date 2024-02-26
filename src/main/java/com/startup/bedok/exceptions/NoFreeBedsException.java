package com.startup.bedok.exceptions;

public class NoFreeBedsException extends RuntimeException{

    public NoFreeBedsException() {
        super("There is no free beds to reserve");
    }
}
