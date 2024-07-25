package com.startup.bedok.exceptions;

import java.util.UUID;

public class AdvertisementNotExistsException extends RuntimeException{

    public AdvertisementNotExistsException(String id) {
        super(String.format("Advertisement with id: %s not exists", id));
    }

    public AdvertisementNotExistsException(UUID id) {
        super(String.format("Advertisement with id: %s not exists", id.toString()));
    }
}
