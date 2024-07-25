package com.startup.bedok.exceptions;

import java.util.UUID;

public class AdvertisementGroupNotExistsException extends RuntimeException{

    public AdvertisementGroupNotExistsException(String id) {
        super(String.format("Advertisement group with id: %s not exists", id));
    }

    public AdvertisementGroupNotExistsException(UUID id) {
        super(String.format("Advertisement group with id: %s not exists", id.toString()));
    }
}
