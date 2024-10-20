package com.startup.bedok.exceptions;

import com.startup.bedok.reservation.model.entity.Reservation;

public class PaymentLinkGenerationException extends RuntimeException{

    public PaymentLinkGenerationException() {
        super("We were unable to connect with payU, please try later");
    }

}
