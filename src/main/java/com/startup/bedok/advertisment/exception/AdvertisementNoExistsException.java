package com.startup.bedok.advertisment.exception;

public class AdvertisementNoExistsException extends RuntimeException{

    public AdvertisementNoExistsException(String id) {
        super(String.format("Advertisement with id: %s not exists", id));
    }
}
