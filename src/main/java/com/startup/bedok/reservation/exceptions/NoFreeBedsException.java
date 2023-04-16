package com.startup.bedok.reservation.exceptions;

public class NoFreeBedsException extends RuntimeException{

    public NoFreeBedsException() {
        super("There is no free beds to reserve");
    }
}
