package com.startup.bedok.user.notification;

import com.startup.bedok.payment.Payment;
import com.startup.bedok.reservation.service.ReservationMapper;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMapper {

    private final ReservationMapper reservationMapper;

        public NotificationAcceptanceDTO mapToNotificationAcceptanceDTO(Notification notification){
            ApplicationUser user = notification.getReservation().getUser();
            Payment payment = notification.getPayment();
            return new NotificationAcceptanceDTO(notification, user, reservationMapper.mapToReservationDTO(notification.getReservation(), payment.getPaymentStatus()));
        }

    public NotificationPaymentDTO mapToNotificationPaymentDTO(Notification notification) {
        Payment payment = notification.getPayment();
            return new NotificationPaymentDTO(reservationMapper.mapToReservationDTO(notification.getReservation(), payment.getPaymentStatus()),
                    notification.getCreatedDate(),
                    Double.parseDouble(payment.getAmountToPay()),
                    null);
    }
}
