package com.startup.bedok.exceptions;

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(String id) {
        super(String.format("Notification with id: %s not exists", id));
    }
}
