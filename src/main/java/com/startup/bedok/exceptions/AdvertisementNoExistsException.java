package com.startup.bedok.exceptions;

import java.util.UUID;

public class AdvertisementNoExistsException extends RuntimeException{

    public AdvertisementNoExistsException(String id) {
        super(String.format("Advertisement with id: %s not exists", id));
    }

    public AdvertisementNoExistsException(UUID id) {
        super(String.format("Advertisement with id: %s not exists", id.toString()));
    }
}
