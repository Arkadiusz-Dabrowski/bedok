package com.startup.bedok.exceptions;

public class AdvertisementNotExistsException extends RuntimeException{

    public AdvertisementNotExistsException(String id) {
        super(String.format("Advertisement with id: %s not exists", id));
    }
}
