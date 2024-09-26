package com.startup.bedok.payment;

import com.startup.bedok.exceptions.PaymentNotFoundException;
import com.startup.bedok.payment.model.OrderCreateRequest;
import com.startup.bedok.payment.model.OrderCreateResponse;
import com.startup.bedok.payment.model.notify.PayUNotification;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.entity.ReservationStatus;
import com.startup.bedok.reservation.repository.ReservationRepository;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PayUOrderService payUOrderService;
    private final ReservationRepository reservationRepository;

    public OrderCreateResponse createPaymentRequest(OrderCreateRequest payuRequest, ApplicationUser user, Reservation reservation){
        Payment payment =  new Payment(payuRequest, user, reservation);
        OrderCreateResponse payuResponse = payUOrderService.order(payuRequest);
        payment.setOrderId(payuResponse.getOrderId());
        paymentRepository.save(payment);
        return payuResponse;
    }

    public PaymentStatus getPaymentStatusForReservation(Reservation reservation){
        Payment payment = paymentRepository.getByReservation(reservation).orElse( null);
        if(payment == null){
            return PaymentStatus.WAITING;
        }
        return payment.getPaymentStatus();
    }

    @Transactional
    public void managePaymentStatus(PayUNotification notification){
        log.info("payment notification: " + notification.toString());
        Payment payment = paymentRepository.findByOrderId(notification.order().orderId()).orElseThrow(() -> new PaymentNotFoundException(notification.order().orderId()));
        if(notification.order().status().equals("COMPLETED")) {
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.getReservation().setReservationStatus(ReservationStatus.PAID);
            reservationRepository.save(payment.getReservation());
        }
        paymentRepository.save(payment);
    }
}
