package com.startup.bedok.payment;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;
    private Double amountToPay;
    private String paymentLink;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime createdDate;

    public Payment(Double amountToPay,
                   String paymentLink,
                   PaymentStatus paymentStatus,
                   LocalDateTime createdDate) {
        this.amountToPay = amountToPay;
        this.paymentLink = paymentLink;
        this.paymentStatus = paymentStatus;
        this.createdDate = createdDate;
    }
}
