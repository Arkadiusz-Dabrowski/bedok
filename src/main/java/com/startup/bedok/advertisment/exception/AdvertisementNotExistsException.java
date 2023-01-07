package com.startup.bedok.advertisment.exception;

public class AdvertisementNotExistsException extends RuntimeException{

    public AdvertisementNotExistsException(String id) {
        super(String.format("Advertisement with id: %s not exists", id));
    }
}
