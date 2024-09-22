package com.startup.bedok.exceptions;

import com.startup.bedok.reservation.model.entity.Reservation;

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(String id) {
        super(String.format("Payment with orderId: %s not exist", id));
    }
    public PaymentNotFoundException(Reservation reservation) {
        super(String.format("There is no payment connected with reservation: %s", reservation.getId().toString()));
    }

}
