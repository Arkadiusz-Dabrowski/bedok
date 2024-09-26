package com.startup.bedok.user.notification;

import com.startup.bedok.payment.Payment;
import com.startup.bedok.payment.PaymentStatus;
import com.startup.bedok.reservation.service.ReservationMapper;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationMapper {

    private final ReservationMapper reservationMapper;

        public NotificationAcceptanceDTO mapToNotificationAcceptanceDTO(Notification notification, PaymentStatus paymentStatus){
            ApplicationUser user = notification.getReservation().getUser();
            return new NotificationAcceptanceDTO(notification, user, reservationMapper.mapToReservationDTO(notification.getReservation(), paymentStatus));
        }

//    public NotificationPaymentDTO mapToNotificationPaymentDTO(Notification notification) {
//        PaymentStatus paymentStatus =  null;
//        if(notification.getPayment() != null){
//            paymentStatus = notification.getPayment().getPaymentStatus();
//        }
//            return new NotificationPaymentDTO(reservationMapper.mapToReservationDTO(notification.getReservation(), paymentStatus),
//                    notification.getCreatedDate(),
//                    Double.parseDouble(payment.getAmountToPay()),
//                    null);
//    }
}
