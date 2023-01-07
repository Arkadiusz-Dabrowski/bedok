package com.startup.bedok.helpers;

import com.startup.bedok.advertisment.exception.AdvertisementNotExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({AdvertisementNotExistsException.class})
    public ResponseEntity<Object> handleAdvertisementNotExistsException(
            AdvertisementNotExistsException ex) {
        return new ResponseEntity<Object>(
                ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
