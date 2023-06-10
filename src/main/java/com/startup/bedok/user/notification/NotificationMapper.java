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
            return new NotificationAcceptanceDTO(reservationMapper.mapToReservationDTO(notification.getReservation()),
                    notification.getCreatedDate(),
                    notification.getNotificationType(),
                    user.getName(), user.getLanguage(), user.isViber(),  user.isSignal(), user.isWhatsapp(), user.isTelegram(),
                    LocalDate.now().getYear() - user.getDateOfBirth().getYear());
        }

    public NotificationPaymentDTO mapToNotificationPaymentDTO(Notification notification) {
        Payment payment = notification.getPayment();
            return new NotificationPaymentDTO(reservationMapper.mapToReservationDTO(notification.getReservation()),
                    notification.getCreatedDate(),
                    payment.getAmountToPay(),
                    payment.getPaymentLink());
    }
}
