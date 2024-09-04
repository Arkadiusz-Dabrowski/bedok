package com.startup.bedok.payment;

import com.startup.bedok.przelewy24.P24Request;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private int amountToPay;
    private String sessionId;
    private String orderId;
    private String currency;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private String sign;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    private LocalDateTime createdDate;

    public Payment() {
    }

    public Payment(P24Request request,PaymentStatus status, ApplicationUser user) {
        this.amountToPay = request.getAmount();
        this.sessionId = request.getSessionId();
        this.sign =request.getSign();
        this.paymentStatus = status;
        this.user = user;
        this.createdDate = LocalDateTime.now();
    }

}
