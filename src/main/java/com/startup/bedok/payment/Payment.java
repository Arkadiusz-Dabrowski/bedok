package com.startup.bedok.payment;

import com.startup.bedok.payment.model.OrderCreateRequest;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;

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
    private String amountToPay;
    @OneToOne
    private Reservation reservation;
    private String sessionId;
    @Column(unique = true)
    private String orderId;
    private String currency;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApplicationUser user;
    private LocalDateTime createdDate;

    public Payment() {
    }

    public Payment(OrderCreateRequest request,  ApplicationUser user, Reservation reservation) {
        this.reservation = reservation;
        this.amountToPay = request.getTotalAmount();
        this.paymentStatus = PaymentStatus.WAITING;
        this.sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        this.user = user;
        this.createdDate = LocalDateTime.now();
    }

}
