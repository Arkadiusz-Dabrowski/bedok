package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.service.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMapper {

    private final ReservationMapper reservationMapper;

        public NotificationDTO mapToNotificationDTO(Notification notification){
            return new NotificationDTO(reservationMapper.mapToReservationDTO(notification.getReservation()),
                    notification.getCreatedDate(),
                    notification.getNotificationType(),
                    notification.getReservation().getUser().getId());
        }
}
