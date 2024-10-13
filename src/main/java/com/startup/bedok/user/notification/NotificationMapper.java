package com.startup.bedok.user.notification;

import com.startup.bedok.reservation.service.ReservationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationMapper {

    private final ReservationMapper reservationMapper;

        public NotificationDTO mapToNotificationDto(Notification notification){
            return new NotificationDTO(notification, reservationMapper.mapToReservationDTO(notification.getReservation()), notification.getMessage());
        }
}
