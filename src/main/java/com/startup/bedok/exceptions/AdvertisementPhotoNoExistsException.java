package com.startup.bedok.exceptions;

public class AdvertisementPhotoNoExistsException extends RuntimeException{

    public AdvertisementPhotoNoExistsException(String id) {
        super(String.format("Advertisement photo with id: %s not exists", id));
    }
}
