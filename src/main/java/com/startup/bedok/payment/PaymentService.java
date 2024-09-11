package com.startup.bedok.payment;

import com.startup.bedok.payment.model.OrderCreateRequest;
import com.startup.bedok.payment.model.OrderCreateResponse;
import com.startup.bedok.payment.model.notify.PayUNotification;
import com.startup.bedok.reservation.controller.ReservationController;
import com.startup.bedok.reservation.model.entity.Reservation;
import com.startup.bedok.reservation.model.entity.ReservationStatus;
import com.startup.bedok.reservation.repository.ReservationRepository;
import com.startup.bedok.reservation.service.ReservationService;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@RequiredArgsConstructor
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

    @Transactional
    public void managePaymentStatus(PayUNotification notification){
        Payment payment = paymentRepository.findById(notification.order().orderId()).orElseThrow(() -> new RuntimeException("No payment with this id"));
        if(notification.order().status() == "COMPLETED"){
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.getReservation().setReservationStatus(ReservationStatus.PAID);
            reservationRepository.save(payment.getReservation());
        }
        paymentRepository.save(payment);
    }
}
