package com.startup.bedok.exceptions;

public class NoFreeBedsException extends RuntimeException{

    public NoFreeBedsException() {
        super("No available beds for reservation.");
    }
}
