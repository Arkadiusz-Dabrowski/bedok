package com.startup.bedok.user.notification;

import com.startup.bedok.payment.Payment;
import com.startup.bedok.reservation.service.ReservationMapper;
import com.startup.bedok.user.model.ApplicationUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class NotificationMapper {

    private final ReservationMapper reservationMapper;

        public NotificationAcceptanceDTO mapToNotificationAcceptanceDTO(Notification notification){
            ApplicationUser user = notification.getReservation().getUser();
            return new NotificationAcceptanceDTO(notification, user, reservationMapper.mapToReservationDTO(notification.getReservation()));
        }

    public NotificationPaymentDTO mapToNotificationPaymentDTO(Notification notification) {
        Payment payment = notification.getPayment();
            return new NotificationPaymentDTO(reservationMapper.mapToReservationDTO(notification.getReservation()),
                    notification.getCreatedDate(),
                    (double) payment.getAmountToPay(),
                    null);
    }
}
