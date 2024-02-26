package com.startup.bedok.exceptions;

public class AdvertisementNoExistsException extends RuntimeException{

    public AdvertisementNoExistsException(String id) {
        super(String.format("Advertisement with id: %s not exists", id));
    }
}
